package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@Scope("session")
public class LogoutJugadorPresentador {
    @PostMapping("/logout-jugador")
    public Commands logout(HttpSession session) {
        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        FachadaServicios.getInstancia().cerrarSesionJugador(jugador);
        session.removeAttribute("jugadorLogueado");
        return Commands.create(
                new Command("redirigirLogin", "/login-jugador.html"));
    }
}
