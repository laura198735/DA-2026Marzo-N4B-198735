package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Deprecated
public class LogoutAdminPresentador {
    @PostMapping("/logout-administrador")
    public Commands logout(HttpSession session) {
        session.invalidate();
        return Commands.create(
                new Command("redirigirLogin", "/login-administrador.html"));
    }
}
