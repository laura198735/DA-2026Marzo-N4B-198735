package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@Scope("session")
public class LogoutAdminPresentador {
    @PostMapping("/logout-administrador")
    public Commands logout(HttpSession session) {
        session.invalidate();
        return Commands.create(
                new Command("redirigirLogin", "/login-admin.html"));
    }
}
