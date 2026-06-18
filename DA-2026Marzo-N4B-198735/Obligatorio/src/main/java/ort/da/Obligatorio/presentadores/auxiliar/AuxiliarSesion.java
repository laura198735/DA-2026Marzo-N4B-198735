package ort.da.Obligatorio.presentadores.auxiliar;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.presentadores.Command;
import ort.da.Obligatorio.presentadores.Commands;

public final class AuxiliarSesion {
    private AuxiliarSesion() {
    }

    public static boolean usuarioAdministradorLogueado(HttpSession session) {
        return session.getAttribute("administradorLogueado") != null;
    }

    public static Administrador obtenerAdministradorLogueado(HttpSession session) {
        Object valorSesion = session.getAttribute("administradorLogueado");
        return extraerUsuario(valorSesion, Administrador.class);
    }

    public static boolean usuarioJugadorLogueado(HttpSession session) {
        return session.getAttribute("jugadorLogueado") != null;
    }

    public static Jugador obtenerJugadorLogueado(HttpSession session) {
        Object valorSesion = session.getAttribute("jugadorLogueado");
        return extraerUsuario(valorSesion, Jugador.class);
    }

    private static <T extends Usuario> T extraerUsuario(Object valorSesion, Class<T> tipoEsperado) {
        if (valorSesion == null) {
            return null;
        }

        if (tipoEsperado.isInstance(valorSesion)) {
            return tipoEsperado.cast(valorSesion);
        }

        if (valorSesion instanceof Login login && tipoEsperado.isInstance(login.getUsuario())) {
            return tipoEsperado.cast(login.getUsuario());
        }

        return null;
    }

    public static Commands redirigirLoginAdmin() {
        return Commands.create(new Command("redirigirLogin", "/login-administrador.html"));
    }

    public static Commands redirigirLoginJugador() {
        return Commands.create(new Command("redirigirLogin", "/login-jugador.html"));
    }
}
