package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.JornadaDto;
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

        Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");

        if (jornadaActual == null) {
            jornadaActual = FachadaServicios.getInstancia().getJornadaActual();
            session.setAttribute("jornadaActual", jornadaActual);
        }

        return Commands.create(
                new Command("mostrarJornadaActual", new JornadaDto(jornadaActual)));
    }

    /**
     * Fecha de la jornada actual (inicialmente es la jornada de la fecha actual o
     * la más próxima anterior si no hay jornada en el día, luego podrá ser cambiada por el usuario)
     */
    // utiliza la jornada actual guardada en sesión para obtener los datos
    // correspondientes a esa jornada y enviarlos a la vista mediante Commands
    @PostMapping("/mostrar-datos-tablero")
    public Commands seleccionarJornada(
            HttpSession session,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaSeleccionada)
            throws HipodromoException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        Jornada jornadaSeleccionada = FachadaServicios
            .getInstancia()
            .getJornadaActual();

        if (jornadaSeleccionada == null) {
            return Commands.create(new Command("error", "No existe una jornada para la fecha seleccionada"));
        }

        session.setAttribute("jornadaActual", jornadaSeleccionada);

        return Commands.create(
                new Command("mostrarJornadaActual", new JornadaDto(jornadaSeleccionada)));
    }

    @PostMapping()
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        // Obtener jornada actual del estado de la vista
        Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");
        if (jornadaActual == null) {
            List<Jornada> jornadas = FachadaServicios.getInstancia().getJornadas();
            if (jornadas != null && !jornadas.isEmpty()) {
                jornadaActual = FachadaServicios.getInstancia().getJornadaActual();
                session.setAttribute("jornadaActual", jornadaActual);
            } else {
                return Commands.create(new Command("error", "No hay jornadas disponibles"));
            }
        }

        // Pedimos los datos a la FachadaServicios

        double totalApostado = FachadaServicios.getInstancia().getTotalApostado();
        double totalPagado = FachadaServicios.getInstancia().getTotalPagado();
        double totalComisionesJornada = FachadaServicios.getInstancia().getTotalComisionesJornada();
        double balanceJornada = FachadaServicios.getInstancia().getBalanceJornada();
        int cantidadCarrerasJornada  = FachadaServicios.getInstancia().getCantidadCarrerasJornada();
        int cantidadCarrerasFinalizadas = FachadaServicios.getInstancia()
                .cantidadCarrerasFinalizadasJornada();
         //    * • Cantidad de carreras que faltan por correr en la jornada actual
        int cantidadProximasCarreras = FachadaServicios.getInstancia().getCantidadProximasCarrerasJornada();
       List<Carrera> listaCarrerasFinalizadasOrdenadas = FachadaServicios.getInstancia().getResultadosCarrerasJornada();
/*
         * • Carreras Finalizadas en la jornada actual ordenadas por número descendente
         * Información: numero, hora de
         * finalización, cantidad de caballos que participaron, total apostado, total
         * pagado, caballo ganador, dividendo final
         * del ganador*/ 


/**     * • Próximas carreras– son las carreras que no están Finalizadas (Información:
         * numero, estado, cantidad de caballos,
         * total apostado, cantidad de apuestas) */

        /**
         
         * 2) Opcionalmente el administrador indica que desea avanzar o retroceder una
         * jornada.
         * 3) El sistema actualiza la información correspondiente a la jornada
         * establecida por el usuario
         * 
         * 4) Opcionalmente el usuario indica que desea gestionar una de las carreras
         * próximas a correrse.
         * 5) El sistema ejecuta el caso de uso Gestionar una carrera para la carrera
         * especificada.
         */

        /**
         * implementar en FachadaServicios
         * Carreras Finalizadas en la jornada actual ordenadas por número descendente
         * Información: numero, hora de
         * finalización, cantidad de caballos que participaron, total apostado, total
         * pagado, caballo ganador, dividendo final
         * del ganador
         */
        List<Carrera> resultadosCarreras = FachadaServicios.getInstancia().getResultadosCarrerasJornada() ;
        /**
         * todo: implementar en FachadaServicios
         * Próximas carreras– son las carreras que no están Finalizadas (Información:
         * numero, estado, cantidad de caballos,
         * total apostado, cantidad de apuestas)
         */
        List<Carrera> proximasCarreras = FachadaServicios.getInstancia().getListaProximasCarrerasJornada();
        return Commands.create(
            new Command("mostrarTotalApostado", totalApostado),
            new Command("mostrarTotalPagado", totalPagado),
            new Command("mostrarTotalComisionesJornada", totalComisionesJornada),
            new Command("mostrarBalanceJornada", balanceJornada),
            new Command("mostrarCantidadCarreras", cantidadCarrerasJornada),
            new Command("mostrarCantidadCarrerasFinalizadas", cantidadCarrerasFinalizadas),
            new Command("mostrarCantidadCarrerasPendientes", cantidadProximasCarreras),
            new Command("mostrarCantidadProximasCarreras", cantidadProximasCarreras),
            new Command("mostrarProximasCarreras", proximasCarreras),
            new Command("mostrarResultadosCarreras", resultadosCarreras));
    }
}