package ort.da.Obligatorio.presentadores;

import java.util.Date;
import java.util.List;

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
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dtos.CarreraDto;

import ort.da.Obligatorio.dtos.JornadaTableroDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.observer.Observable;
import ort.da.Obligatorio.observer.Observador;
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
@Scope("session")

public class TableroAdminPresentador implements Observador {
    private ConexionNavegador conexionNavegador;
    private HttpSession session;

    private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

    public TableroAdminPresentador(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    //subscribo el tablero  a carrera como Observador.
    private void subscribirACarreras(Jornada jornada) {
    if (jornada == null || jornada.getCarreras() == null) {
        return;
    }

    for (Carrera carrera : jornada.getCarreras()) {
        if (carrera != null) {
            carrera.subscribir(this);
        }
    }
}

    @PostMapping("/seleccionar-jornada")
    public Commands seleccionarJornada(@RequestParam("jornadaId") int numeroJornada, HttpSession session)throws HipodromoException {
        this.session = session;
      
        if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginAdmin();
        }

        Jornada jornadaSeleccionada = fachadaServicios.buscarJornadaPorNumero(numeroJornada);
      
        if (jornadaSeleccionada == null) {
            return Commands.create(new Command("error", "Jornada no encontrada"));
        } 

        session.setAttribute("jornadaActual", jornadaSeleccionada);
          subscribirACarreras(jornadaSeleccionada);
      
        return construirTablero(fachadaServicios.getJornadas(), jornadaSeleccionada);
    }

    @PostMapping("/cargar-datos-tablero")
    public Commands cargarDatosTablero(HttpSession session) throws HipodromoException {
        this.session = session;
        if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
            return AuxiliarSesion.redirigirLoginAdmin();
        }

        List<Jornada> jornadas = fachadaServicios.getJornadas();
        Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");
        if (jornadaActual == null) {
            jornadaActual = fachadaServicios.getJornadaActual();
            session.setAttribute("jornadaActual", jornadaActual);
        }
        if (jornadaActual == null) {
            return Commands.create(new Command("error", "No hay jornadas disponibles"));
        }

        session.setAttribute("jornadaActual", jornadaActual);//guarda la jormada actual en la session
         subscribirACarreras(jornadaActual);
        return construirTablero(jornadas, jornadaActual);
    }

    private Commands construirTablero(List<Jornada> jornadas, Jornada jornadaActual) throws HipodromoException {
        double totalApostado = fachadaServicios.getTotalApostado(jornadaActual);
        double totalPagado = fachadaServicios.getTotalPagado(jornadaActual);
        double totalComisionesJornada = fachadaServicios.getTotalComisionesJornada(jornadaActual);
        double balanceJornada = fachadaServicios.getBalanceJornada(jornadaActual);

        int cantidadCarrerasJornada = fachadaServicios.getCantidadCarrerasJornada(jornadaActual);
        int cantidadCarrerasFinalizadas = fachadaServicios.getCantidadCarrerasFinalizadasJornada(jornadaActual);
        // *Carreras Finalizadas en la jornada actual ordenadas por número descendente
        // Información: numero, hora de finalización, cantidad de caballos que
        // participaron, total apostado, total pagado, caballo ganador, dividendo final
        // del ganador */
        List<Carrera> carrerasFinalizadasOrdenadas = fachadaServicios
                .getResultadosCarrerasFinalizadasJornadaOrdenadas(jornadaActual);
        int cantidadProximasCarreras = fachadaServicios.getCantidadProximasCarrerasJornada(jornadaActual);
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
                new Command("mostrarResultadosCarreras",
                        carrerasFinalizadasOrdenadas.stream()
                                .filter(carrera -> carrera != null)
                                .map(CarreraDto.CarreraFinalizadaDto::new)
                        .toList()),
                new Command("mostrarProximasCarreras", proximasCarreras.stream()
                        .filter(carrera -> carrera != null)
                        .map(CarreraDto.CarreraProximaDto::new)
                        .toList()));
    }
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public SseEmitter registrarSSE(HttpSession session) {
    this.session = session;
    conexionNavegador.conectarSSE();
    return conexionNavegador.getConexionSSE();
}
    @Override
    public void actualizar(Observable origen, Object evento) {
        if (!(origen instanceof Carrera carrera) || !(evento instanceof Observable.Evento eventoCarrera)) {
            return;
        }

        switch (eventoCarrera) {
            case APUESTA_AGREGADA:
            case ESTADO_CARRERA_MODIFICADO:
            case CARRERA_TOTAL_APOSTADO_ACTUALIZADO:
            case CARRERA_TOTAL_PAGADO_ACTUALIZADO:
            case CARRERA_DIVIDENDO_ACTUALIZADO:
            case CARRERA_DIVIDENDO_FINAL_ACTUALIZADO:
                break;
            default:
                return;
        }

        if (conexionNavegador == null || session == null) {
            return;
        }

        try {
            Jornada jornadaActual = (Jornada) session.getAttribute("jornadaActual");
            if (jornadaActual == null) {
                jornadaActual = carrera.getJornada();
            }

            if (jornadaActual == null) {
                return;
            }

            session.setAttribute("jornadaActual", jornadaActual);
            conexionNavegador.enviarCommands(construirTablero(fachadaServicios.getJornadas(), jornadaActual));
        } catch (HipodromoException e) {
            conexionNavegador.enviarCommands(Commands.create(new Command("error", e.getMessage())));
        }
    }
}
