package ort.da.Obligatorio.presentadores;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dtos.CarreraDto;
import ort.da.Obligatorio.dtos.JornadaTableroDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

/* Fecha de la jornada actual (inicialmente es la jornada de la fecha actual o la más próxima anterior si no hay 
jornada en el día, luego podrá ser cambiada por el usuario)  
• Total apostado en la jornada actual 
• Total pagado en la jornada actual  
• Total de comisiones cobradas en la jornada actual 
• Balance general de la jornada (total apostado – total pagado) 
• Cantidad de carreras de la jornada actual 
• Cantidad de carreras Finalizadas en la jornada actual 
• Cantidad de carreras que faltan por correr en la jornada actual 
 */
@RestController
@RequestMapping("/tablero-administrador")
public class TableroAdminPresentador {
    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

  

    @PostMapping("/seleccionar-jornada")
    public Commands seleccionarJornada(@RequestParam("jornadaId") int numeroJornada, HttpSession session)
            throws HipodromoException {
        if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginAdmin();
        }

        Jornada jornadaSeleccionada = fachadaServicios.buscarJornadaPorNumero(numeroJornada);
        if (jornadaSeleccionada == null) {
            return Commands.create(new Command("error", "Jornada no encontrada"));
        }

        session.setAttribute("jornadaActual", jornadaSeleccionada);
        return construirTablero(fachadaServicios.getJornadas(), jornadaSeleccionada);// hago getJornadas para actualizar el listado de jornadas en el tablero por si se agregó una nueva jornada mientras el admin estaba en el tablero
    }

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginAdmin();
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

    private Commands construirTablero(List<Jornada> jornadas, Jornada jornadaActual) throws HipodromoException {
        double totalApostado = fachadaServicios.getTotalApostado(jornadaActual);
        double totalPagado = fachadaServicios.getTotalPagado(jornadaActual);
        double totalComisionesJornada = fachadaServicios.getTotalComisionesJornada(jornadaActual);
        double balanceJornada = fachadaServicios.getBalanceJornada(jornadaActual);

        int cantidadCarrerasJornada = fachadaServicios.getCantidadCarrerasJornada(jornadaActual);
        int cantidadCarrerasFinalizadas = fachadaServicios.getCantidadCarrerasFinalizadasJornada(jornadaActual);
        //*Carreras Finalizadas en la jornada actual ordenadas por número descendente Información: numero, hora de finalización, cantidad de caballos que participaron, total apostado, total pagado, caballo ganador, dividendo final del ganador   */
        List<Carrera> carrerasFinalizadas = fachadaServicios.getResultadosCarrerasFinalizadasJornadaOrdenadas(jornadaActual);
        int cantidadProximasCarreras = fachadaServicios.
        getCantidadProximasCarrerasJornada(jornadaActual);

        List<Carrera> resultadosCarreras = fachadaServicios.getResultadosCarrerasFinalizadasJornadaOrdenadas(jornadaActual);
        List<Carrera> proximasCarreras = fachadaServicios.getListaProximasCarrerasJornada(jornadaActual);
        List<JornadaTableroDto> jornadasTablero = jornadas == null ? List.of()
                : jornadas.stream()
                        .filter(jornada -> jornada != null)
                        .map(jornada -> JornadaTableroDto.from(jornada.getNumero(), jornada.getDia()))
                        .toList();

        return Commands.create(
                new Command("mostrarJornadas", jornadasTablero),
                new Command("mostrarJornadaActual",
                        JornadaTableroDto.from(jornadaActual.getNumero(), jornadaActual.getDia())),
                new Command("mostrarTotalApostado", totalApostado),
                new Command("mostrarTotalPagado", totalPagado),
                new Command("mostrarTotalComisionesJornada", totalComisionesJornada),
                new Command("mostrarBalanceJornada", balanceJornada),
                new Command("mostrarCantidadCarreras", cantidadCarrerasJornada),
                new Command("mostrarCantidadCarrerasFinalizadas", cantidadCarrerasFinalizadas),
                new Command("mostrarCantidadProximasCarreras", cantidadProximasCarreras),
                new Command("mostrarResultadosCarreras", carrerasFinalizadas.stream()
                        .filter(carrera -> carrera != null)
                        .map(CarreraDto.CarreraFinalizadaDto::new)
                        .toList()),
                new Command("mostrarProximasCarreras", proximasCarreras.stream()
                        .filter(carrera -> carrera != null)
                        .map(CarreraDto.CarreraProximaDto::new)
                        .toList()));
    }

}
