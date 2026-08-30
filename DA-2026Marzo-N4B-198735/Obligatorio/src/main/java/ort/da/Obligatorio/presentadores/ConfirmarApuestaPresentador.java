package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.IModalidad;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dtos.CarreraDto;
import ort.da.Obligatorio.dtos.ParticipanteDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.ApuestaRealizadaDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.ModalidadDisponibleDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/confirmar-apuesta")
@Scope("session")
public class ConfirmarApuestaPresentador {

    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    @PostMapping("/confirmar")
    public Commands confirmarApuesta(
            HttpSession session,
            @RequestParam("numeroCarrera") int numeroCarrera,
            @RequestParam("numeroCaballo") int numeroCaballo,
            @RequestParam("modalidad") String modalidadNombre,
            @RequestParam("monto") double monto,
            @RequestParam("password") String password) throws HipodromoException {

        if (!LoginJugadorPresentador.usuarioJugadorLogueado(session)) {
            return Commands.create(new Command("redirigirLogin", "/login-jugador.html"));
        }

        Jugador jugador = (Jugador) session.getAttribute("jugadorLogueado");
        if (jugador == null) {
            return Commands.create(new Command("error", "No hay un jugador logueado."));
        }

        if (monto <= 0) {
            return Commands.create(new Command("error", "El monto de la apuesta debe ser mayor a cero."));
        }

        IModalidad modalidad = fachadaServicios.obtenerModalidadPorNombre(modalidadNombre);
        if (modalidad == null) {
            return Commands.create(new Command("error", "Tipo de apuesta invalido."));
        }

        Carrera carrera = fachadaServicios.buscarCarreraPorNumero(numeroCarrera);
        if (carrera == null) {
            return Commands.create(new Command("error", "No se encontro la carrera seleccionada."));
        }
        if (!carrera.puedeApostar()) {
            return Commands.create(new Command("error", "La carrera seleccionada no esta abierta para apostar."));
        }

        Participante participante = carrera.obtenerParticipanteEnCarrera(numeroCaballo);
        if (participante == null || participante.getCaballo() == null) {
            return Commands.create(new Command("error", "El caballo seleccionado no participa en la carrera."));
        }

        Apuesta apuesta = new Apuesta(monto, modalidad, participante);
        Caballo caballo = participante.getCaballo();
        try {
            // El presentador solo orquesta el caso de uso y delega la regla de negocio
            // al servicio de aplicación. El servicio coordina el agregado Jugador
            // y valida la contraseña antes de registrar la apuesta.
            fachadaServicios.realizarApuesta(jugador, apuesta, password);
        } catch (HipodromoException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
        carrera.agregarApuesta(caballo, apuesta);
        fachadaServicios.confirmarApuesta(apuesta);
        session.setAttribute("jugadorLogueado", jugador);
        session.removeAttribute("apuestaEnCurso");

        return Commands.create(
                new Command("mostrarMensaje", "Apuesta confirmada correctamente."),
                new Command("guardarApuestaConfirmada", crearApuestaRealizadaDto(apuesta)),
                new Command("redirigirTableroJugador", "tablero-jugador.html"));
    }

    @PostMapping("/descartar")
    public Commands descartarApuesta(HttpSession session) {
        session.removeAttribute("apuestaEnCurso");
        return Commands.create(
                new Command("mostrarMensaje", "Apuesta descartada. El saldo no fue modificado."),
                new Command("redirigirTableroJugador", "tablero-jugador.html"));
    }

    @GetMapping()
    public Commands mostrarPantalla(@RequestParam(value = "numeroApuesta", required = false) Integer numeroApuesta,
            HttpSession session) throws HipodromoException {

        if (!LoginJugadorPresentador.usuarioJugadorLogueado(session)) {
            return Commands.create(new Command("redirigirLogin", "/login-jugador.html"));
        }

        if (numeroApuesta == null || numeroApuesta <= 0) {
            return Commands.create(new Command("error", "No se recibio el numero de apuesta."));
        }

        Carrera carreraSeleccionada = (Carrera) session.getAttribute("carreraSeleccionada");
        int numeroCarrera = carreraSeleccionada != null ? carreraSeleccionada.getNumeroCarrera() : -1;
        System.out.println("Numero de carrera recibido: " + numeroCarrera);

        if (carreraSeleccionada == null) {
            return Commands.create(new Command("error", "No se encontro la carrera con numero: " + numeroCarrera));
        }

        Caballo caballoSeleccionado = (Caballo) session.getAttribute("caballoSeleccionado");
        Participante participante = fachadaServicios.obtenerParticipante(caballoSeleccionado, carreraSeleccionada);
        IModalidad modalidadApuestaSeleccionada = fachadaServicios.buscarModalidadPorNumeroApuesta(carreraSeleccionada,
                numeroApuesta);
        ModalidadDisponibleDto modalidadDto = modalidadApuestaSeleccionada == null ? null
                : new ModalidadDisponibleDto(modalidadApuestaSeleccionada.getNombre());
        double dividendoActual = participante != null ? participante.getDividendoActual() : 0.0;
        double montoApostado = participante != null ? participante.getTotalApostadoAlCaballo() : 0.0;
        Jugador jugadorLogueado = (Jugador) session.getAttribute("jugadorLogueado");
        double saldoJugador = jugadorLogueado != null ? jugadorLogueado.getSaldo() : 0.0;

        session.setAttribute("carreraSeleccionada", carreraSeleccionada);
        session.removeAttribute("caballoSeleccionado");

        return Commands.create(
                new Command("mostrarNumeroCarrera", new CarreraDto(carreraSeleccionada)),
                new Command("mostrarCaballo", ParticipanteDto.fromCarrera(carreraSeleccionada)),
                new Command("mostrarModalidadApuesta", modalidadDto),
                new Command("mostrarDividendoActual", dividendoActual),
                new Command("mostrarMontoApostado", montoApostado),
                new Command("mostrarMontoADebitarDelSaldo", saldoJugador));
    }

    private ApuestaRealizadaDto crearApuestaRealizadaDto(Apuesta apuesta) {
        Participante participante = apuesta.getParticipante();
        Carrera carrera = participante == null ? null : participante.getCarrera();
        Caballo caballo = participante == null ? null : participante.getCaballo();

        return new ApuestaRealizadaDto(
                carrera == null || carrera.getJornada() == null ? null : carrera.getJornada().getDia(),
                carrera == null ? 0 : carrera.getNumeroCarrera(),
                carrera == null ? "" : carrera.getNombre(),
                caballo == null ? 0 : caballo.getNumero(),
                caballo == null ? "" : caballo.getNombre(),
                apuesta.getMonto(),
                apuesta.getModalidad() == null ? "" : apuesta.getModalidad().getNombre(),
                null,
                null,
                "Por correr");
    }
}
