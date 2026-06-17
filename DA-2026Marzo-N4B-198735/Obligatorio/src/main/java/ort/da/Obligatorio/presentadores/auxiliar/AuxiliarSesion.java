package ort.da.Obligatorio.presentadores.auxiliar;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.presentadores.Command;
import ort.da.Obligatorio.presentadores.Commands;

public final class AuxiliarSesion {
    private AuxiliarSesion() {
    }

    public static boolean usuarioAdministradorLogueado(HttpSession session) {
        return session.getAttribute("administradorLogueado") != null;
    }

    public static Administrador obtenerAdministradorLogueado(HttpSession session) {
        return (Administrador) session.getAttribute("administradorLogueado");
    }

    public static boolean usuarioJugadorLogueado(HttpSession session) {
        return session.getAttribute("jugadorLogueado") != null;
    }

    public static Jugador obtenerJugadorLogueado(HttpSession session) {
        return (Jugador) session.getAttribute("jugadorLogueado");
    }

    public static Commands redirigirLoginAdmin() {
        return Commands.create(new Command("redirigirLogin", "/login-admin.html"));
    }

    public static Commands redirigirLoginJugador() {
        return Commands.create(new Command("redirigirLogin", "/login-jugador.html"));
    }
}
