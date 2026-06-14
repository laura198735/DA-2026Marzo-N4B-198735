package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Modalidad;
import ort.da.Obligatorio.dominio.Usuario;
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

    public List<Jornada> getJornadas() throws HipodromoException {
        try {
            return servicioJornada.getJornadas();

        } catch (Exception e) {
            throw new HipodromoException("Error al obtener las jornadas: " + e.getMessage());
        }
    }

    public void agregarJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) {
            throw new HipodromoException("No se puede agregar una jornada nula");
        }

        servicioJornada.agregar(jornada);
    }

    public Jornada getJornadaActual() throws HipodromoException {
        try {
            return servicioJornada.getJornadaActual();
        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la jornada actual: ");
        }
    }

    public Jornada getJornadaPorFecha(Date fechaSeleccionada) throws HipodromoException {
        try {
            Jornada jornada = servicioJornada.getJornadaPorFecha(fechaSeleccionada);

            if (jornada == null) {
                throw new HipodromoException("No existe una jornada para la fecha seleccionada");
            }

            return jornada;

        } catch (HipodromoException e) {
            throw e;
        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la jornada por fecha: " + e.getMessage());
        }
    }

    // **Tablero Administrador** */
    // Fecha de la jornada actual (inicialmente es la jornada de la fecha actual o
    // la más próxima anterior si no hay
    // jornada en el día, luego podrá ser cambiada por el usuario) se recibe de la
    // seleccionada por el usuario en el presentador que se muestra en pantalla//
    public double getTotalApostado(Jornada jornada) throws HipodromoException {
        return jornada.getTotalApostado();
    }

    public double getTotalPagado(Jornada jornada) throws HipodromoException {
        return servicioJornada.getTotalPagado(jornada);
    }

    public double getTotalComisionesJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getTotalComisionesJornada(jornada);
    }

    // total apostado - total pagado en la jornada
    public double getBalanceJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getBalanceJornada(jornada);
    }

    public int getCantidadCarrerasJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getCantidadCarrerasJornada(jornada);
    }

    // * Cantidad de carreras Finalizadas en la jornada actual
    public int getCantidadCarrerasFinalizadasJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getCantidadCarrerasFinalizadasJornada(jornada);
    }

    public int getCantidadProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getCantidadProximasCarrerasJornada(jornada);

    }

    public List<Carrera> getResultadosCarrerasJornadaOrdenadas(Jornada jornada) throws HipodromoException {
        return servicioJornada.getResultadosCarrerasJornadaOrdenadas(jornada);
    }

    public List<Carrera> getListaProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        return servicioJornada.getListaProximasCarrerasJornada(jornada);
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



       
}