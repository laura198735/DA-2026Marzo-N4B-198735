package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@Scope("session") // Mantener el estado del administrador logueado en la sesión
public class LoginAdminPresentador {

    public LoginAdminPresentador() {
    }

    @GetMapping("/login-administrador")
    public Commands mostrarLogin() {
        return Commands.create(new Command("redirigirLogin", "/login-administrador.html"));
    }

    @PostMapping("/login-administrador")// Autenticación del administrador
    public Commands login(HttpSession session, @ModelAttribute CredencialDto credencialDto) {
        try {
            Credencial credencial = credencialDto.toCredencial();
            System.out.println("Nombre recibido: " + credencial.getNombre());
            System.out.println("Password recibido: " + credencial.getPassword());

            Login loginAdministrador = FachadaServicios.getInstancia().autenticarAdministrador(credencial);
            Administrador administrador = (Administrador) loginAdministrador.getUsuario();
            session.removeAttribute("jugadorLogueado");
            session.setAttribute("administradorLogueado", administrador);

            System.out.println("Nombre recibido: " + administrador.getNombreUsuario());

            return Commands.create(new Command("redirigir-tablero-admin", "/tablero-administrador.html"));
        } catch (AutenticacionException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
    }

    @PostMapping("/logout-administrador")
    public Commands logout(HttpSession session) {
        // Se obtiene el usuario actual desde la sesión y se cierra usando el
        // comportamiento polimórfico del tipo real (Administrador).
        Usuario usuarioLogueado = (Usuario) session.getAttribute("administradorLogueado");
        if (usuarioLogueado != null) {
            FachadaServicios.getInstancia().cerrarSesion(usuarioLogueado);
        }
        session.removeAttribute("administradorLogueado");
        return Commands.create(new Command("redirigirLogin", "/login-administrador.html"));
    }

     public static boolean usuarioAdministradorLogueado(HttpSession session) {
        return session.getAttribute("administradorLogueado") != null;
    }

 
}