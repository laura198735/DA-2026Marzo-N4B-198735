package ort.da.Obligatorio.presentadores;

import java.util.List;

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
import ort.da.Obligatorio.dtos.JornadaDto;
import ort.da.Obligatorio.dtos.JornadaDto.CarreraTableroDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/tablero-administrador")
public class TableroAdminPresentador {
    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        if (!usuarioAdministradorLogueado(session)) {
            return redirigirLogin();
        }

        List<Jornada> jornadas = fachadaServicios.getJornadas();
        Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");
        if (jornadaActual == null) {
            jornadaActual = fachadaServicios.getJornadaActual();
        }

        if (jornadaActual == null) {
            return Commands.create(new Command("error", "No hay jornadas disponibles"));
        }

        session.setAttribute("jornadaActual", jornadaActual);
        return construirTablero(jornadas, jornadaActual);
    }

    @PostMapping("/seleccionar-jornada")
    public Commands seleccionarJornada(@RequestParam("jornadaId") int numeroJornada, HttpSession session)
            throws HipodromoException {
        if (!usuarioAdministradorLogueado(session)) {
            return redirigirLogin();
        }

        Jornada jornadaSeleccionada = buscarJornadaPorNumero(numeroJornada);
        if (jornadaSeleccionada == null) {
            return Commands.create(new Command("error", "Jornada no encontrada"));
        }

        session.setAttribute("jornadaActual", jornadaSeleccionada);
        return construirTablero(fachadaServicios.getJornadas(), jornadaSeleccionada);
    }

    private Commands construirTablero(List<Jornada> jornadas, Jornada jornadaActual) throws HipodromoException {
        double totalApostado = fachadaServicios.getTotalApostado(jornadaActual);
        double totalPagado = fachadaServicios.getTotalPagado(jornadaActual);
        double totalComisionesJornada = fachadaServicios.getTotalComisionesJornada(jornadaActual);
        double balanceJornada = fachadaServicios.getBalanceJornada(jornadaActual);

        int cantidadCarrerasJornada = fachadaServicios.getCantidadCarrerasJornada(jornadaActual);
        int cantidadCarrerasFinalizadas = fachadaServicios.cantidadCarrerasFinalizadasJornada(jornadaActual);
        int cantidadProximasCarreras = fachadaServicios.getCantidadProximasCarrerasJornada(jornadaActual);

        List<Carrera> resultadosCarreras = fachadaServicios.getResultadosCarrerasJornadaOrdenadas(jornadaActual);
        List<Carrera> proximasCarreras = fachadaServicios.getListaProximasCarrerasJornada(jornadaActual);

        return Commands.create(
                new Command("mostrarJornadas", jornadas == null ? List.of() : JornadaDto.fromList(jornadas)),
                new Command("mostrarJornadaActual", new JornadaDto(jornadaActual)),
                new Command("mostrarTotalApostado", totalApostado),
                new Command("mostrarTotalPagado", totalPagado),
                new Command("mostrarTotalComisionesJornada", totalComisionesJornada),
                new Command("mostrarBalanceJornada", balanceJornada),
                new Command("mostrarCantidadCarreras", cantidadCarrerasJornada),
                new Command("mostrarCantidadCarrerasFinalizadas", cantidadCarrerasFinalizadas),
                new Command("mostrarCantidadCarrerasPendientes", cantidadProximasCarreras),
                new Command("mostrarCantidadProximasCarreras", cantidadProximasCarreras),
                new Command("mostrarResultadosCarreras", resultadosCarreras == null ? List.of()
                        : resultadosCarreras.stream().map(this::crearCarreraTablero).toList()),
                new Command("mostrarProximasCarreras", proximasCarreras == null ? List.of()
                        : proximasCarreras.stream().map(this::crearCarreraTablero).toList())
        );
    }

    private boolean usuarioAdministradorLogueado(HttpSession session) {
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");
        return usuarioAdministrador != null;
    }

    private Commands redirigirLogin() {
        return Commands.create(new Command("redirigirLogin", "/login-admin.html"));
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

    private CarreraTableroDto crearCarreraTablero(Carrera carrera) {
        if (carrera == null) {
            return new CarreraTableroDto(0, "", "Sin estado", 0, 0.0, 0.0, "-", "-", 0);
        }

        return new CarreraTableroDto(
                carrera.getNumeroCarrera(),
                carrera.getNombre(),
                obtenerEstadoCarrera(carrera),
                obtenerCantidadCaballos(carrera),
                carrera.getTotalApostado(),
                carrera.getTotalPagado(),
                obtenerCaballoGanador(carrera),
                obtenerDividendoFinal(carrera),
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

    private String obtenerDividendoFinal(Carrera carrera) {
        return "-";
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
}
