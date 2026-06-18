package ort.da.Obligatorio.presentadores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.IModalidad;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dtos.CarreraDto;
import ort.da.Obligatorio.dtos.ParticipanteDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.ModalidadDisponibleDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/confirmar-apuesta")
@Scope("session")
public class ConfirmarApuestaPresentador {

    private ConexionNavegador conexionNavegador;

    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    public ConfirmarApuestaPresentador(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    public void confirmarApuesta() {
        fachadaServicios.confirmarApuesta(null);
        conexionNavegador.enviarCommands(
                Commands.create(new Command("apuestaConfirmada", "La apuesta ha sido confirmada.")));
    }

    @GetMapping()
    public Commands mostrarPantalla(@RequestParam(value = "numeroApuesta", required = false) Integer numeroApuesta,
            HttpSession session) throws HipodromoException {

        if (!AuxiliarSesion.usuarioJugadorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginAdmin();
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
        double saldoJugador = AuxiliarSesion.obtenerJugadorLogueado(session).getSaldo();

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
}
