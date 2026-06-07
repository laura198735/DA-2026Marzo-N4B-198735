package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Modalidad;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.JornadaDto;
import ort.da.Obligatorio.dominio.Finalizada;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.excepciones.HipodromoException;

public class FachadaServicios {
    private static FachadaServicios instancia;
    private ServicioAutenticacion servicioAutenticacion;
    private ServicioJornada servicioJornada;

    private List<Usuario> usuarios; // Lista de usuarios para autenticación
    private List<Modalidad> modalidades; // Lista de modalidades de apuesta

    public static FachadaServicios getInstancia() {
        if (instancia == null) {
            instancia = new FachadaServicios();
        }
        return instancia;
    }

    private FachadaServicios() {
        // Inicializar servicios
        this.servicioAutenticacion = new ServicioAutenticacion();
        this.servicioJornada = new ServicioJornada();
        this.usuarios = new ArrayList<>();
        this.modalidades = new ArrayList<>();

    }

    public Usuario autenticar(Credencial credencial) throws AutenticacionException {
        return servicioAutenticacion.autenticar(credencial);
    }

    // **Tablero Administrador** */
    // Fecha de la jornada actual (inicialmente es la jornada de la fecha actual o
    // la más próxima anterior si no hay
    // jornada en el día, luego podrá ser cambiada por el usuario)
    public List<Jornada> getJornadas() throws HipodromoException {
        try {
            return servicioJornada.getJornadas();

        } catch (Exception e) {
            throw new HipodromoException("Error al obtener las jornadas: " + e.getMessage());
        }
    }

    public double getTotalApostado() throws HipodromoException {
        return servicioJornada.getJornadaActual().getTotalApostado();
    }

    public double getTotalPagado() throws HipodromoException {
        return servicioJornada.getJornadaActual().getTotalPagado();
    }

    public double getTotalComisionesJornada() throws HipodromoException {
        return servicioJornada.getJornadaActual().getTotalComisiones();
    }

    // total apostado - total pagado en la jornada
    public double getBalanceJornada() throws HipodromoException {
        return servicioJornada.getJornadaActual().getBalanceJornada();
    }

    public int getCantidadCarrerasJornada() {
        return servicioJornada.getJornadaActual().getCantidadCarrerasJornada();
    }

    // * Cantidad de carreras Finalizadas en la jornada actual
    public int cantidadCarrerasFinalizadasJornada() throws HipodromoException {
        return servicioJornada.getCantidadCarrerasFinalizadasJornada();
    }

    public int getCantidadProximasCarrerasJornada() {
        return servicioJornada.getCantidadProximasCarrerasJornada();
    }

    public List<Carrera> getResultadosCarrerasJornadaOrdenadas() throws HipodromoException {
        return servicioJornada.getResultadosCarrerasJornadaOrdenadas((servicioJornada.getJornadaActual()));
    }

    public List<Carrera> getListaProximasCarrerasJornada() throws HipodromoException {
        return servicioJornada.getJornadaActual().getListaProximasCarrerasJornada();
    }  
    /*
     * • Carreras Finalizadas en la jornada actual ordenadas por número descendente
     * Información: numero, hora de
     * finalización, cantidad de caballos que participaron, total apostado, total
     * pagado, caballo ganador, dividendo final
     * del ganador
     * • Próximas carreras– son las carreras que no están Finalizadas (Información:
     * numero, estado, cantidad de caballos,
     * total apostado, cantidad de apuestas)
        */

    public Jornada getJornadaActual() throws HipodromoException {
        try {
            return servicioJornada.getJornadaActual();
        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la jornada actual: " + e.getMessage());
        }
    }

}