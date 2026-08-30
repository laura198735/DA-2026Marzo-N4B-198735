package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.AutenticacionException;

public class ServicioAutenticacion {
    private List<Administrador> administradores;
    private List<Jugador> jugadores;
    private List<Login> logins;

    public ServicioAutenticacion() {
        this.administradores = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.logins = new ArrayList<>();
        this.administradores.add(new Administrador("a1", "a1"));
        this.administradores.add(new Administrador("a2", "a2"));

        Jugador jugador1 = new Jugador("j1", "j1");
        jugador1.setNombre("Carlos Méndez");
        jugador1.setSaldo(14850);
        this.jugadores.add(jugador1);

        Jugador jugador2 = new Jugador("j2", "j2");
        jugador2.setNombre("Ana Rodríguez");
        jugador2.setSaldo(12000);
        this.jugadores.add(jugador2);
    }

    public ServicioAutenticacion(List<Administrador> administradores, List<Jugador> jugadores) {
        // Inicializar con la lista proporcionada 
        this.administradores = administradores == null ? new ArrayList<>() : new ArrayList<>(administradores);
        this.jugadores = jugadores == null ? new ArrayList<>() : new ArrayList<>(jugadores);
        this.logins = new ArrayList<>();
    }

    /*
     * public Usuario autenticar(Credencial credencial) throws
     * AutenticacionException {
     * for (Administrador admin : administradores) {
     * if (admin.validar(credencial)) {
     * registrarLogin(admin);
     * return admin;
     * }
     * }
     * 
     * for (Jugador jugador : jugadores) {
     * if (jugador.validar(credencial)) {
     * registrarLogin(jugador);
     * return jugador;
     * }
     * }
     * 
     * throw new AutenticacionException("Credenciales inválidas");
     * }
     */

    public List<Login> getLogins() {
        return new ArrayList<>(logins);
    }

    // verifico que no tenga sesin activa.
    public Login autenticarAdministrador(Credencial credencial) throws AutenticacionException {
        for (Administrador admin : administradores) {
            if (admin.validar(credencial)) {
                if (yaTieneSesionActiva(admin)) {
                    throw new AutenticacionException("El administrador ya tiene una sesión activa");
                }
                Login nuevoLogin = new Login(new Date(), admin);
                logins.add(nuevoLogin);
                return nuevoLogin;
            }
        }
        throw new AutenticacionException("Credenciales invalidas para administrador");
    }

    // jugador puede tener varias sesiones activas.
    public Login autenticarJugador(Credencial credencial) throws AutenticacionException {
        for (Jugador jugador : jugadores) {
            if (jugador.validar(credencial)) {
                Login nuevoLogin = new Login(new Date(), jugador);
                logins.add(nuevoLogin);
                return nuevoLogin;
            }
        }

        throw new AutenticacionException("Credenciales invalidas para jugador");
    }

    public void cerrarSesionAdministrador(Administrador administrador) {
        // Este método se mantiene por compatibilidad, pero delega en la
        // implementación polimórfica del usuario.
        cerrarSesion((Usuario) administrador);
    }

    public void cerrarSesionJugador(Jugador jugador) {
        // El cierre de sesión del jugador usa el mismo flujo genérico.
        cerrarSesion((Usuario) jugador);
    }

    public void cerrarSesion(Usuario usuario) {
        // La lógica de eliminación es única para todos los usuarios, pero la
        // invocación se realiza a través del polimorfismo.
        if (usuario == null) {
            return;
        }

        for (int i = 0; i < logins.size(); i++) {
            Login login = logins.get(i);
            if (login != null
                    && login.getUsuario() != null
                    && login.getUsuario().getClass().equals(usuario.getClass())
                    && login.getUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())) {
                logins.remove(i);
                break;
            }
        }
    }

    // Un mismo administrador no podrá ingresar a la aplicación simultáneamente con
    // las mismas credenciales.
    private boolean yaTieneSesionActiva(Usuario usuario) {
        for (Login login : logins) {
            if (login != null
                    && login.getUsuario() != null
                    && login.getUsuario().getClass().equals(usuario.getClass())
                    && login.getUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())) {
                return true;
            }
        }

        return false;
    }

}
