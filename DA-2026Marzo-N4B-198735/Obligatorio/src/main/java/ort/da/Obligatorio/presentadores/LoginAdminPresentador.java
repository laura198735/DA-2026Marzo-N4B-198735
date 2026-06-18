package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/login-administrador")
@Scope("session")
public class LoginAdminPresentador {
   
    @PostMapping()
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
}
