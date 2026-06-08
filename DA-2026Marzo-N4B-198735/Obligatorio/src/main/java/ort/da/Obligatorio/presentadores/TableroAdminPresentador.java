package ort.da.Obligatorio.presentadores;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/tablero-administrador")
public class TableroAdminPresentador {

    @GetMapping("/tablero")
    public Commands mostrarTablero(HttpSession session) throws HipodromoException {
        if (!usuarioAdministradorLogueado(session)) {
            return redirigirLogin();
        }

        Jornada jornadaActual = obtenerJornadaDeSesion(session);
        if (jornadaActual == null) {
            return Commands.create(new Command("error", "No hay jornadas disponibles"));
        }

        return crearComandosTablero(jornadaActual);
    }

    @PostMapping("/seleccionar-jornada")
    //obtiene el numero de jornada seleccionado desde el formulario y lo busca  para mostrar su tablero
    public Commands seleccionarJornada(
            HttpSession session,
            @RequestParam("jornadaId") int jornadaId)
            throws HipodromoException {

        if (!usuarioAdministradorLogueado(session)) {
            return redirigirLogin();
        }

        Jornada jornadaSeleccionada = buscarJornadaPorNumero(jornadaId);
        if (jornadaSeleccionada == null) {
            return Commands.create(new Command("error", "No existe la jornada seleccionada"));
        }

        session.setAttribute("jornadaActual", jornadaSeleccionada);
        return crearComandosTablero(jornadaSeleccionada);
    }

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        if (!usuarioAdministradorLogueado(session)) {
            return redirigirLogin();
        }

        Jornada jornadaActual = obtenerJornadaDeSesion(session);
        if (jornadaActual == null) {
            return Commands.create(new Command("error", "No hay jornadas disponibles"));
        }

        return crearComandosTablero(jornadaActual);
    }

    private boolean usuarioAdministradorLogueado(HttpSession session) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");
        return usuarioAdministrador != null;
    }

    private Commands redirigirLogin() {
        return Commands.create(new Command("redirigirLogin", "/login-admin.html"));
    }

    private Jornada obtenerJornadaDeSesion(HttpSession session) throws HipodromoException {
        Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");

        if (jornadaActual == null) {
            jornadaActual = FachadaServicios.getInstancia().getJornadaActual();
            if (jornadaActual != null) {
                session.setAttribute("jornadaActual", jornadaActual);
            }
        }

        return jornadaActual;
    }

    private Jornada buscarJornadaPorNumero(int numeroJornada) throws HipodromoException {
        List<Jornada> jornadas = FachadaServicios.getInstancia().getJornadas();
        if (jornadas == null) {
            return null;
        }

        return jornadas.stream()
                .filter(jornada -> jornada != null && jornada.getNumero() == numeroJornada)
                .findFirst()
                .orElse(null);
    }

    private Commands crearComandosTablero(Jornada jornadaActual) throws HipodromoException {
        List<JornadaResumen> jornadas = FachadaServicios.getInstancia().getJornadas().stream()
                .map(this::crearJornadaResumen)
                .toList();
        List<CarreraTablero> proximasCarreras = jornadaActual.getListaProximasCarrerasJornada().stream()
                .map(this::crearCarreraTablero)
                .toList();
        List<CarreraTablero> resultadosCarrerasOrdenadas = jornadaActual.getResultadosCarrerasJornada().stream()
                .sorted(Comparator.comparingInt(Carrera::getNumeroCarrera).reversed())
                .map(this::crearCarreraTablero)
                .toList();

        return Commands.create(
                new Command("mostrarJornadas", jornadas),
                new Command("mostrarJornadaActual", crearJornadaResumen(jornadaActual)),
                new Command("mostrarTotalApostado", jornadaActual.getTotalApostado()),
                new Command("mostrarTotalPagado", jornadaActual.getTotalPagado()),
                new Command("mostrarTotalComisionesJornada", jornadaActual.getTotalComisiones()),
                new Command("mostrarBalanceJornada", jornadaActual.getBalanceJornada()),
                new Command("mostrarCantidadCarreras", jornadaActual.getCantidadCarrerasJornada()),
                new Command("mostrarCantidadCarrerasFinalizadas", jornadaActual.getCantidadCarrerasFinalizadasJornada()),
                new Command("mostrarCantidadCarrerasPendientes", jornadaActual.getCantidadProximasCarrerasJornada()),
                new Command("mostrarProximasCarreras", proximasCarreras),
                new Command("mostrarResultadosCarreras", resultadosCarrerasOrdenadas));
    }

    private JornadaResumen crearJornadaResumen(Jornada jornada) {
        return new JornadaResumen(jornada.getNumero(), jornada.getDia());
    }

    private CarreraTablero crearCarreraTablero(Carrera carrera) {
        return new CarreraTablero(
                carrera.getNumeroCarrera(),
                carrera.getNombre(),
                obtenerEstadoCarrera(carrera),
                obtenerCantidadCaballos(carrera),
                carrera.getTotalApostado(),
                carrera.getTotalPagado(),
                obtenerCaballoGanador(carrera),
                "-",
                obtenerCantidadApuestas(carrera));
    }

    private String obtenerEstadoCarrera(Carrera carrera) {
        return carrera.getEstadoCarrera() == null
                ? "Sin estado"
                : carrera.getEstadoCarrera().getClass().getSimpleName();
    }

    private int obtenerCantidadCaballos(Carrera carrera) {
        return carrera.getRegistros() == null ? 0 : carrera.getRegistros().size();
    }

    private String obtenerCaballoGanador(Carrera carrera) {
        return carrera.getCaballoGanador() == null ? "-" : carrera.getCaballoGanador().getNombre();
    }

    private int obtenerCantidadApuestas(Carrera carrera) {
        if (carrera.getRegistros() == null) {
            return 0;
        }

        int cantidad = 0;
        for (Participante participante : carrera.getRegistros()) {
            List<Apuesta> apuestas = participante.getApuestas();
            if (apuestas != null) {
                cantidad += apuestas.size();
            }
        }
        return cantidad;
    }

    public static record JornadaResumen(int numero, Date dia) {
    }

    public static record CarreraTablero(
            int numeroCarrera,
            String nombre,
            String estadoCarrera,
            int cantidadCaballos,
            double totalApostado,
            double totalPagado,
            String caballoGanador,
            String dividendoFinal,
            int cantidadApuestas) {
    }
}
