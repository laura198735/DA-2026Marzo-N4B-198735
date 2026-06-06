package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
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

    // **Tablero Administrador

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
        return servicioJornada.getBalanceJornadaActual();
    }

    public int getCantidadCarrerasJornada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCantidadCarrerasJornada'");
    }

    public int getCantidadCarrerasPendientesJornada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCantidadCarrerasPendientesJornada'");
    }

    public int getCantidadCarrerasFinalizadasJornada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCantidadCarrerasFinalizadasJornada'");
    }

    public List<Carrera> getResultadosCarrerasJornada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResultadosCarrerasJornada'");
    }

    public List<Carrera> getProximasCarrerasJornada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProximasCarrerasJornada'");
    }

    // Delegator for obtaining the current jornada
    public Jornada getJornadaActual() throws HipodromoException {
        try {
            return servicioJornada.getJornadaActual();
        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la jornada actual: " + e.getMessage());
        }
    }
}
