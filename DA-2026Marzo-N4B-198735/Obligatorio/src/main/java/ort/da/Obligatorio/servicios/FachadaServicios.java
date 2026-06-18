package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.IModalidad;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.observer.Observable;
import ort.da.Obligatorio.observer.Observador;
import ort.da.Obligatorio.dominio.Simple;

public class FachadaServicios extends Observable {
    private static FachadaServicios instancia;
    private ServicioAutenticacion servicioAutenticacion;
    private ServicioJornada servicioJornada;
    private ServicioCarrera servicioCarrera;
    private ServicioApuesta servicioApuesta;

    private List<Usuario> usuarios; // Lista de usuarios para autenticación
    private List<Caballo> caballos; // Lista de caballos para gestión de carreras
    private List<IModalidad> modalidades; // Lista de modalidades de apuesta

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
        this.servicioApuesta = new ServicioApuesta();
        this.servicioCarrera = new ServicioCarrera(this.servicioJornada, this.servicioApuesta);
        this.caballos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.modalidades = new ArrayList<>();

    }

    // usuarios conectados
    public List<Login> getLogins() {
        return servicioAutenticacion.getLogins();
    }

    public Login autenticarAdministrador(Credencial credencial) throws AutenticacionException {
        Login login = servicioAutenticacion.autenticarAdministrador(credencial);
        notificar(Evento.ADMINISTRADOR_CONECTADO);
        return login;
    }

    public Login autenticarJugador(Credencial credencial) throws AutenticacionException {
        Login login = servicioAutenticacion.autenticarJugador(credencial);
        notificar(Evento.JUGADOR_CONECTADO);
        return login;
    }

    public void cerrarSesionAdministrador(Administrador administrador) {
        servicioAutenticacion.cerrarSesionAdministrador(administrador);
        notificar(Evento.ADMINISTRADOR_CONECTADO);
    }

    public void cerrarSesionJugador(Jugador jugador) {
        servicioAutenticacion.cerrarSesionJugador(jugador);
        notificar(Evento.JUGADOR_CONECTADO);
    }

    public void subscribirAutenticacion(Observador observador) {
        subscribir(observador);
    }

    // tablero administrador
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

    public List<Carrera> getResultadosCarrerasFinalizadasJornadaOrdenadas(Jornada jornada) throws HipodromoException {
        return servicioJornada.getResultadosCarrerasFinalizadasJornadaOrdenadas(jornada);
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

    // CASO DE USO GESTIONAR CARRERA
    // busco la carrera por numero en la jornada seleccionada porque Jornada tiene
    // las carreras.
    public Carrera buscarCarreraEnJornada(Jornada jornada, int numeroCarrera) throws HipodromoException {
        if (jornada == null) {
            throw new HipodromoException("No hay jornada seleccionada");
        }
        if (numeroCarrera <= 0) {
            throw new HipodromoException("Número de carrera inválido: " + numeroCarrera);
        } /* las carreras se cargan en Jornadas en la precarga de datos */
        if (jornada.getCarreras() == null || jornada.getCarreras().isEmpty()) {
            throw new HipodromoException("La jornada seleccionada no tiene carreras");
        }
        for (Carrera carrera : jornada.getCarreras()) {
            if (carrera != null && carrera.getNumeroCarrera() == numeroCarrera) {
                return carrera;
            }
        }

        throw new HipodromoException("No se encontró la carrera con número: " + numeroCarrera
                + " en la jornada seleccionada");
    }

    // servicio carrera.
    public List<Carrera> getCarreras() throws HipodromoException {
        return servicioCarrera.getCarreras();
    }

    public void gestionAbrirCarrera(Carrera carrera) throws HipodromoException {
        if (carrera == null) {
            throw new HipodromoException("Carrera inválida");
        }
        servicioCarrera.abrirCarrera(carrera);
    }

    public void gestionarCerrarCarrera(int numeroCarrera) throws HipodromoException {
        servicioCarrera.cerrarCarrera(numeroCarrera);
    }

    public void gestionarFinalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        servicioCarrera.finalizarCarreraConGanador(carrera, caballoGanador);
    }

    public void gestionarFinalizarCarreraYPagar(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        servicioCarrera.finalizarCarreraYPagar(carrera, caballoGanador);
    }

    public Jornada buscarJornadaPorNumero(int numeroJornada) throws HipodromoException {
        return servicioJornada.buscarJornadaPorNumero(numeroJornada);

    }

    public Carrera buscarCarreraPorNumero(int numeroCarrera) throws HipodromoException {
        return servicioCarrera.buscarCarreraPorNumero(numeroCarrera);
    }

    public boolean caballoParticipaEnCarrera(int numeroCaballo, int numeroCarrera) throws HipodromoException {
        return servicioCarrera.buscarCarreraPorNumero(numeroCarrera) != null && servicioCarrera
                .buscarCaballoParticipaEnCarrera(servicioCarrera.buscarCarreraPorNumero(numeroCarrera), numeroCaballo);
    }

    // caballos
    public Caballo buscarCaballoPorNumero(int numeroCaballo) throws HipodromoException {
        return servicioCarrera.buscarCaballoPorNumero(numeroCaballo);
    }

    public List<Caballo> getCaballosCarrera(Carrera carrera) throws HipodromoException {
        return servicioCarrera.getCaballosCarrera(carrera);
    }

    public List<IModalidad> getModalidadesDisponibles() {
        return servicioApuesta.getModalidadesDisponibles();
    }

    // apuestas
    public List<Apuesta> getApuestasCarrera(Carrera carrera) throws HipodromoException {
        return servicioApuesta.getApuestasCarrera(carrera);
    }

    // modalidad
    public IModalidad obtenerModalidadPorNombre(String nombreModalidad) {
        return servicioApuesta.getModalidadesDisponibles().stream()
                .filter(modalidad -> modalidad != null && modalidad.getNombre().equalsIgnoreCase(nombreModalidad))
                .findFirst()
                .orElse(null);
    }

    // CU Confirmar apuesta
    public void confirmarApuesta(Apuesta apuesta) {
        servicioApuesta.confirmarApuesta(apuesta);
    }

    public Apuesta buscarApuestaPorNumero(Carrera carrera, int numeroApuesta) {
        return servicioApuesta.buscarApuestaPorNumero(carrera, numeroApuesta);
    }

    public IModalidad buscarModalidadPorNumeroApuesta(Carrera carrera, int numeroApuesta) {
        return servicioApuesta.buscarModalidadPorNumeroApuesta(carrera, numeroApuesta);
    }

    public Participante obtenerParticipante(Caballo caballo, Carrera carrera) {
        return servicioCarrera.obtenerParticipante(caballo, carrera);
    }
}
