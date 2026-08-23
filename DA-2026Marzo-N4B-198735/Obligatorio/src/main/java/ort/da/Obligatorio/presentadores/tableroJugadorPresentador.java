package ort.da.Obligatorio.presentadores;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
import ort.da.Obligatorio.observer.Observable;
import ort.da.Obligatorio.observer.Observador;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/tablero-jugador")
@Scope("session")
public class tableroJugadorPresentador implements Observador {

    private ConexionNavegador conexionNavegador;
    private HttpSession session;

    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    public tableroJugadorPresentador(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    /** observa saldo, apuesta, historial de apuestas, total apostado */
    private void suscribirAJugador(Jugador jugador) {
        if (jugador != null) {
            jugador.subscribir(this);
        }
    }

    /** observa cambios en carrera por ej que hace el administrador */
    private void suscribirACarreras(List<Carrera> carreras) {
        if (carreras == null) {
            return;
        }
        for (Carrera carrera : carreras) {
            if (carrera != null) {
                carrera.subscribir(this);
            }
        }
    }

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        if (!AuxiliarSesion.usuarioJugadorLogueado(session)) {// si no hay un jugador logueado, redirigir al login
            return AuxiliarSesion.redirigirLoginJugador();
        }

        this.session = session;
        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        suscribirAJugador(jugador);
        suscribirACarreras(fachadaServicios.getCarreras());
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
        this.session = session;
        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        session.setAttribute("jugadorLogueado", jugador);
        Apuesta apuesta = new Apuesta(jugador, monto, modalidadNombre, numeroCarrera, numeroCaballo);
        return construirTablero(jugador);
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE(HttpSession session) {
        this.session = session;
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
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
        String nombre = fachadaServicios.nombreVisible(jugador);
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
        if (jugador == null) {
            return List.of();
        }

        Map<Integer, Apuesta> apuestas = new LinkedHashMap<>();

        if (jugador.getApuestas() != null) {
            jugador.getApuestas().stream()
                    .filter(apuesta -> apuesta != null)
                    .forEach(apuesta -> apuestas.put(apuesta.getId(), apuesta));
        }

        try {
            List<Carrera> carreras = fachadaServicios.getCarreras();
            if (carreras != null) {
                carreras.stream()
                        .filter(carrera -> carrera != null && carrera.getRegistros() != null)
                        .flatMap(carrera -> carrera.getRegistros().stream())
                        .filter(participante -> participante != null && participante.getApuestas() != null)
                        .flatMap(participante -> participante.getApuestas().stream())
                        .filter(apuesta -> apuesta != null && esApuestaDelJugador(apuesta, jugador))
                        .forEach(apuesta -> apuestas.put(apuesta.getId(), apuesta));
            }
        } catch (HipodromoException e) {
            // Si falla la reconstruccion global, se muestran al menos las apuestas del
            // jugador en sesion.
        }

        List<Apuesta> apuestasConfirmadas = fachadaServicios.getApuestas();
        if (apuestasConfirmadas != null) {
            apuestasConfirmadas.stream()
                    .filter(apuesta -> apuesta != null && esApuestaDelJugador(apuesta, jugador))
                    .forEach(apuesta -> apuestas.put(apuesta.getId(), apuesta));
        }

        return apuestas.values().stream()
                .map(apuesta -> {
                    Date fecha = null;
                    int numeroCarrera = 0;
                    String nombreCarrera = null;
                    int numeroCaballo = 0;
                    String nombreCaballo = null;
                    Double montoCobrado = null;
                    Double dividendoFinal = null;
                    String estado = null;
                    try {
                        Participante p = apuesta.getParticipante();
                        if (p != null) {
                            Carrera c = p.getCarrera();
                            if (c != null) {
                                fecha = c.getJornada() == null ? null : c.getJornada().getDia();
                                numeroCarrera = c.getNumeroCarrera();
                                nombreCarrera = c.getNombre();
                                estado = c.obtenerNombreEstadoCarrera();
                            }
                            Caballo cab = p.getCaballo();
                            if (cab != null) {
                                numeroCaballo = cab.getNumero();
                                nombreCaballo = cab.getNombre();
                            }
                            dividendoFinal = p.getDividendoFinal() > 0 ? p.getDividendoFinal() : null;
                        }
                        if (apuesta.esApuestaGanadora()) {
                            montoCobrado = apuesta.calcularGanancia();
                        }
                    } catch (Exception ex) {
                        // ignorar errores de calculo
                    }

                    return new ApuestaRealizadaDto(fecha,
                            numeroCarrera,
                            nombreCarrera,
                            numeroCaballo,
                            nombreCaballo,
                            apuesta.getMonto(),
                            apuesta.getModalidadNombre(),
                            montoCobrado,
                            dividendoFinal,
                            estado);
                })
                .sorted(Comparator.comparing(ApuestaRealizadaDto::fecha,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    private boolean esApuestaDelJugador(Apuesta apuesta, Jugador jugador) {
        if (apuesta == null || apuesta.getJugador() == null || jugador == null) {
            return false;
        }

        String usuarioApuesta = apuesta.getJugador().getNombreUsuario();
        String usuarioJugador = jugador.getNombreUsuario();
        return usuarioApuesta != null && usuarioApuesta.equals(usuarioJugador);
    }
    // mostrar iniciales jugador
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

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (!(evento instanceof Observable.Evento eventoObservable)) {
            return;
        }

        switch (eventoObservable) {
            case JUGADOR_APUESTA_AGREGADA:
            case JUGADOR_SALDO_ACTUALIZADO:
            case APUESTA_AGREGADA:
            case ESTADO_CARRERA_MODIFICADO:
            case ESTADO_CARRERA_FINALIZADO:
            case CARRERA_DIVIDENDO_ACTUALIZADO:
            case CARRERA_DIVIDENDO_FINAL_ACTUALIZADO:
                break;
            default:
                return;
        }

        if (conexionNavegador == null || session == null) {
            return;
        }

        Jugador jugador = origen instanceof Jugador jugadorOrigen
                ? jugadorOrigen
                : AuxiliarSesion.obtenerJugadorLogueado(session);
        if (jugador == null) {
            return;
        }

        try {
            conexionNavegador.enviarCommands(construirTablero(jugador));
        } catch (HipodromoException e) {
            conexionNavegador.enviarCommands(Commands.create(new Command("error", e.getMessage())));
        }
    }
}
