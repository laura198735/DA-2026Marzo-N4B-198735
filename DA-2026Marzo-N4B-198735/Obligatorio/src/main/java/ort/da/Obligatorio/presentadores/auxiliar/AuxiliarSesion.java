package ort.da.Obligatorio.presentadores.auxiliar;
import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.presentadores.Command;
import ort.da.Obligatorio.presentadores.Commands;

public class AuxiliarSesion {

    private AuxiliarSesion() {
       
    }

    public static boolean usuarioAdministradorLogueado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario instanceof Administrador;
    }

    public static Commands redirigirLoginAdmin() {
        return Commands.create(
                new Command("redirigirLoginAdmin", "/login-admin.html"));
    }


    public static boolean usuarioJugadorLogueado(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario instanceof Administrador;
    }

    public static Commands redirigirLoginJugador() {
        return Commands.create(
                new Command("redirigirLoginJugador", "/login-jugador.html"));
    }
}
