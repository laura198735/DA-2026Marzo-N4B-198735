package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class LogoutJugadorPresentador {
@PostMapping("/logout-jugador")
    public Commands logout(HttpSession session) {
        session.invalidate();
        return Commands.create(
                new Command("redirigirLogin", "/login-jugador.html"));
    }
}
