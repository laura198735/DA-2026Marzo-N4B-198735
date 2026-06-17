package ort.da.Obligatorio.presentadores;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Finalizada;
import ort.da.Obligatorio.dominio.IModalidad;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dtos.TableroJugadorDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.ApuestaRealizadaDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.CaballoDisponibleDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.CarreraDisponibleDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.JugadorResumenDto;
import ort.da.Obligatorio.dtos.TableroJugadorDto.ModalidadDisponibleDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/tablero-jugador")
public class tableroJugadorPresentador {
    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        if (!AuxiliarSesion.usuarioJugadorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginJugador();
        }

        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        return construirTablero(jugador);
    }

    @PostMapping("/confirmar-apuesta")
    public Commands confirmarApuesta(
            HttpSession session,
            @RequestParam("numeroCarrera") int numeroCarrera,
            @RequestParam("numeroCaballo") int numeroCaballo,
            @RequestParam("modalidad") String modalidadNombre,
            @RequestParam("monto") double monto) throws HipodromoException {
        if (!AuxiliarSesion.usuarioJugadorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginJugador();
        }
        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        session.setAttribute("jugadorLogueado", jugador);
        if (jugador == null) {
            return Commands.create(new Command("error", "No hay un jugador logueado."));
        }
        if (monto <= 0) {
            return Commands.create(new Command("error", "El monto de la apuesta debe ser mayor a cero."));
        }
        IModalidad modalidad = fachadaServicios.obtenerModalidadPorNombre(modalidadNombre);//
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
        if (participante == null) {
            return Commands.create(new Command("error", "El caballo seleccionado no participa en la carrera."));
        }
        Caballo caballo = participante.getCaballo();
        if (caballo == null) {
            return Commands.create(new Command("error", "No se pudo identificar el caballo seleccionado."));
        }

        Apuesta apuesta = new Apuesta(monto, modalidad, participante);
        if (apuesta.calcularCosto() > jugador.getSaldo()) {
            return Commands.create(new Command("error", "Saldo insuficiente para realizar la apuesta."));
        }

        jugador.realizarApuesta(apuesta);
        carrera.agregarApuesta(caballo, apuesta);
        fachadaServicios.confirmarApuesta(apuesta);
   
        return Commands.create(
                new Command("mostrarMensaje", "Apuesta confirmada correctamente."),
                new Command("mostrarTableroJugador", crearTableroJugadorDto(jugador)));
    }

    private Commands construirTablero(Jugador jugador) throws HipodromoException {
        return Commands.create(new Command("mostrarTableroJugador", crearTableroJugadorDto(jugador)));
    }

    private TableroJugadorDto crearTableroJugadorDto(Jugador jugador) throws HipodromoException {
        return new TableroJugadorDto(
                crearResumenJugador(jugador),
                crearModalidadesDto(fachadaServicios.getModalidadesDisponibles()),
                crearCarrerasDisponiblesDto(carrerasDisponiblesParaApostar()),
                crearApuestasRealizadasDto(jugador));
    }

    private List<Carrera> carrerasDisponiblesParaApostar() throws HipodromoException {
        List<Carrera> carreras = fachadaServicios.getCarreras();
        if (carreras == null) {
            return List.of();
        }

        return carreras.stream()
                .filter(carrera -> carrera != null && carrera.puedeApostar())
                .sorted(Comparator.comparingInt(Carrera::getNumeroCarrera))
                .toList();
    }

    private JugadorResumenDto crearResumenJugador(Jugador jugador) {
        String nombre = nombreVisible(jugador);
        return new JugadorResumenDto(
                nombre,
                iniciales(nombre),
                jugador == null ? 0.0 : jugador.getSaldo(),
                jugador == null ? 0.0 : jugador.getTotalApostado(),
                jugador == null ? 0.0 : jugador.getTotalGanado());
    }

    private List<ModalidadDisponibleDto> crearModalidadesDto(List<IModalidad> modalidades) {
        return modalidades == null ? List.of()
                : modalidades.stream()
                        .filter(modalidad -> modalidad != null)
                        .map(modalidad -> new ModalidadDisponibleDto(modalidad.getNombre()))
                        .toList();
    }

    private List<CarreraDisponibleDto> crearCarrerasDisponiblesDto(List<Carrera> carreras) {
        return carreras == null ? List.of()
                : carreras.stream()
                        .filter(carrera -> carrera != null)
                        .map(this::crearCarreraDisponibleDto)
                        .toList();
    }

    private CarreraDisponibleDto crearCarreraDisponibleDto(Carrera carrera) {
        List<CaballoDisponibleDto> caballos = carrera.getRegistros() == null ? List.of()
                : carrera.getRegistros().stream()
                        .filter(participante -> participante != null && participante.getCaballo() != null)
                        .map(this::crearCaballoDisponibleDto)
                        .toList();

        Date fecha = carrera.getJornada() == null ? null : carrera.getJornada().getDia();
        return new CarreraDisponibleDto(carrera.getNumeroCarrera(), carrera.getNombre(), fecha, caballos);
    }

    private CaballoDisponibleDto crearCaballoDisponibleDto(Participante participante) {
        Caballo caballo = participante.getCaballo();
        Double dividendo = participante.tieneDividendoValido() ? participante.getDividendoActual() : null;
        return new CaballoDisponibleDto(caballo.getNumero(), caballo.getNombre(), dividendo);
    }

    private List<ApuestaRealizadaDto> crearApuestasRealizadasDto(Jugador jugador) {
        return jugador == null || jugador.getApuestas() == null ? List.of()
                : jugador.getApuestas().stream()
                        .filter(apuesta -> apuesta != null)
                        .map(this::crearApuestaRealizadaDto)
                        .sorted(Comparator.comparing(ApuestaRealizadaDto::fecha,
                                Comparator.nullsLast(Comparator.reverseOrder())))
                        .toList();
    }

    private ApuestaRealizadaDto crearApuestaRealizadaDto(Apuesta apuesta) {
        Participante participante = apuesta.getParticipante();
        Carrera carrera = participante == null ? null : participante.getCarrera();
        Caballo caballo = participante == null ? null : participante.getCaballo();
        boolean finalizada = carrera != null && carrera.getEstadoCarrera() instanceof Finalizada;

        Double montoCobrado = finalizada ? calcularMontoCobrado(apuesta) : null;
        Double dividendoFinal = finalizada && participante != null ? participante.getDividendoFinal() : null;
        Date fecha = carrera == null || carrera.getJornada() == null ? null : carrera.getJornada().getDia();

        return new ApuestaRealizadaDto(
                fecha,
                carrera == null ? 0 : carrera.getNumeroCarrera(),
                carrera == null ? "" : carrera.getNombre(),
                caballo == null ? 0 : caballo.getNumero(),
                caballo == null ? "" : caballo.getNombre(),
                apuesta.getMonto(),
                apuesta.getModalidad() == null ? "" : apuesta.getModalidad().getNombre(),
                montoCobrado,
                dividendoFinal,
                finalizada ? "Finalizada" : "Por correr");
    }

    private double calcularMontoCobrado(Apuesta apuesta) {
        if (!apuesta.esApuestaGanadora()) {
            return 0.0;
        }
        try {
            return apuesta.calcularGanancia();
        } catch (HipodromoException e) {
            return 0.0;
        }
    }

    private String nombreVisible(Jugador jugador) {
        if (jugador == null) {
            return "Jugador";
        }

        String nombre = jugador.getNombre();
        if (nombre != null && !nombre.isBlank()) {
            return nombre;
        }

        String usuario = jugador.getNombreUsuario();
        return usuario == null || usuario.isBlank() ? "Jugador" : usuario;
    }
    //mostrar iniciales ugador
    private String iniciales(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "JG";
        }

        String[] partes = nombre.trim().split("\\s+");
        StringBuilder builder = new StringBuilder();
        for (String parte : partes) {
            if (!parte.isBlank()) {
                builder.append(parte.substring(0, 1).toUpperCase(Locale.ROOT));
            }
            if (builder.length() >= 2) {
                break;
            }
        }

        while (builder.length() < 2) {
            builder.append('J');
        }
        return builder.toString();
    }
}
