package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Modalidad;
import ort.da.Obligatorio.dominio.Usuario;
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

    // **Tablero Administrador

    public List<Jornada> getJornadas() throws HipodromoException {
        try {
            return servicioJornada.getJornadas();

        } catch (Exception e) {
            throw new HipodromoException("Error al obtener las jornadas: " + e.getMessage());
        }
    }

    public double getTotalApostado() throws HipodromoException {
        return getTotalApostado(servicioJornada.getJornadaActual());
    }

    public double getTotalPagado() throws HipodromoException {
        return getTotalPagado(servicioJornada.getJornadaActual());
    }

    public double getTotalComisionesJornada() throws HipodromoException {
        return getTotalComisionesJornada(servicioJornada.getJornadaActual());
    }

    // total apostado - total pagado en la jornada
    public double getBalanceJornada() throws HipodromoException {
        return getBalanceJornada(servicioJornada.getJornadaActual());
    }

    public int getCantidadCarrerasJornada() {
        try {
            return getCantidadCarrerasJornada(servicioJornada.getJornadaActual());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getCantidadCarrerasPendientesJornada() {
        try {
            return getCantidadCarrerasPendientesJornada(servicioJornada.getJornadaActual());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getCantidadCarrerasFinalizadasJornada() {
        try {
            return getCantidadCarrerasFinalizadasJornada(servicioJornada.getJornadaActual());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carrera> getResultadosCarrerasJornada() {
        try {
            return getResultadosCarrerasJornada(servicioJornada.getJornadaActual());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carrera> getProximasCarrerasJornada() {
        try {
            return getProximasCarrerasJornada(servicioJornada.getJornadaActual());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Jornada-aware implementations
    public double getTotalApostado(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getTotalApostado();
    }

    public double getTotalPagado(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getTotalPagado();
    }

    public double getTotalComisionesJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        final double COMISION = 0.10;
        return jornada.getTotalApostado() * COMISION;
    }

    public double getBalanceJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        double totalA = jornada.getTotalApostado();
        double totalP = jornada.getTotalPagado();
        double com = getTotalComisionesJornada(jornada);
        return totalA - totalP - com;
    }

    public int getCantidadCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getCarreras() == null ? 0 : jornada.getCarreras().size();
    }

    public int getCantidadCarrerasPendientesJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        if (jornada.getCarreras() == null) return 0;
        int c = 0;
        for (Carrera ca : jornada.getCarreras()) {
            if (!(ca.getEstadoCarrera() instanceof Finalizada)) c++;
        }
        return c;
    }

    public int getCantidadCarrerasFinalizadasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        if (jornada.getCarreras() == null) return 0;
        int c = 0;
        for (Carrera ca : jornada.getCarreras()) {
            if (ca.getEstadoCarrera() instanceof Finalizada) c++;
        }
        return c;
    }

    public List<Carrera> getResultadosCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        List<Carrera> res = new ArrayList<>();
        if (jornada.getCarreras() == null) return res;
        for (Carrera ca : jornada.getCarreras()) {
            if (ca.getEstadoCarrera() instanceof Finalizada) res.add(ca);
        }
        res.sort((a,b) -> Integer.compare(b.getNumeroCarrera(), a.getNumeroCarrera()));
        return res;
    }

    public List<Carrera> getProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null) throw new HipodromoException("No hay jornada seleccionada");
        List<Carrera> res = new ArrayList<>();
        if (jornada.getCarreras() == null) return res;
        for (Carrera ca : jornada.getCarreras()) {
            if (!(ca.getEstadoCarrera() instanceof Finalizada)) res.add(ca);
        }
        return res;
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
