package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tablero-administrador") // ruta para acceder al tablero del administrador
public class TableroAdminPresentador {

    public TableroAdminPresentador() {

    }

    @GetMapping()
    public Commands mostrarTablero(HttpSession session) throws HipodromoException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        Jornada jornadaActual = FachadaServicios.getInstancia().getJornadaActual();

        return Commands.create(
                new Command("mostrarJornadaActual", jornadaActual));
    }

    @PostMapping()
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        // Pedimos los datos a la Fachada.
        double totalApostado = FachadaServicios.getInstancia().getTotalApostado();
        double totalPagado = FachadaServicios.getInstancia().getTotalPagado();
        double totalComisionesJornada = FachadaServicios.getInstancia().getTotalComisionesJornada();
        double balanceJornada = FachadaServicios.getInstancia().getBalanceJornada();
        int cantidadCarreras = FachadaServicios.getInstancia().getCantidadCarrerasJornada();
        int cantidadCarrerasFinalizadas = FachadaServicios.getInstancia().getCantidadCarrerasFinalizadasJornada();
        int cantidadCarrerasPendientes = FachadaServicios.getInstancia().getCantidadCarrerasPendientesJornada();
        /**todo: implementar en FachadaServicios
         * Carreras Finalizadas en la jornada actual ordenadas por número descendente
         * Información: numero, hora de
         * finalización, cantidad de caballos que participaron, total apostado, total
         * pagado, caballo ganador, dividendo final
         * del ganador
         */
        List<Carrera> resultadosCarreras = FachadaServicios.getInstancia().getResultadosCarrerasJornada();
        /**todo: implementar en FachadaServicios
         * Próximas carreras– son las carreras que no están Finalizadas (Información:
         * numero, estado, cantidad de caballos,
         * total apostado, cantidad de apuestas)
        */
        List<Carrera> proximasCarreras = FachadaServicios.getInstancia().getProximasCarrerasJornada();
        return Commands.create(
                new Command("mostrarTotalApostado", totalApostado),
                new Command("mostrarTotalPagado", totalPagado),
                new Command("mostrarTotalComisionesJornada", totalComisionesJornada),
                new Command("mostrarBalanceJornada", balanceJornada),
                new Command("mostrarCantidadCarreras", cantidadCarreras),
                new Command("mostrarCantidadCarrerasFinalizadas", cantidadCarrerasFinalizadas),
                new Command("mostrarCantidadCarrerasPendientes", cantidadCarrerasPendientes),
                new Command("mostrarResultadosCarreras", resultadosCarreras),
                new Command("mostrarProximasCarreras", proximasCarreras)
        );
    }
}