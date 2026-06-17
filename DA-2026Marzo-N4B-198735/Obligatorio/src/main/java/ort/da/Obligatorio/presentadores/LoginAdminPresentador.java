package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/login-admin")
@Scope("session")
public class LoginAdminPresentador {
   
    @PostMapping()
    public Commands login(HttpSession session, @ModelAttribute CredencialDto credencialDto) {
        try {
            Credencial credencial = credencialDto.toCredencial();
            System.out.println("Nombre recibido: " + credencial.getNombre());
            System.out.println("Password recibido: " + credencial.getPassword());
            
            Administrador administrador = FachadaServicios.getInstancia().autenticarAdministrador(credencial);
            session.removeAttribute("jugadorLogueado");
            session.setAttribute("administradorLogueado", administrador);

            System.out.println("Nombre recibido: " + administrador.getNombre());
          
            return Commands.create(new Command("redirigir-tablero-admin", "/tablero-administrador.html"));
        } catch (AutenticacionException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
    }
}
