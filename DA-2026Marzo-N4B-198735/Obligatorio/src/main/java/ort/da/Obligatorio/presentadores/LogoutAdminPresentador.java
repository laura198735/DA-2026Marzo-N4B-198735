package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@Scope("session")
public class LogoutAdminPresentador {
    @PostMapping("/logout-administrador")
    public Commands logout(HttpSession session) {
        Administrador administrador = AuxiliarSesion.obtenerAdministradorLogueado(session);
        FachadaServicios.getInstancia().cerrarSesionAdministrador(administrador);
        session.removeAttribute("administradorLogueado");
        return Commands.create(
            new Command("redirigirLogin", "/login-administrador.html"));
    }
}
