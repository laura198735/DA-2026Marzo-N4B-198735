package ort.da.Obligatorio.presentadores;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.HipodromoException;


@RequestMapping("/gestionar-carrera")
@RestController
@Scope("session")
public class GestionarCarreraPresentador {

    public GestionarCarreraPresentador() {
    }

  @GetMapping()//maneja la carga inicial del formulario de login
    public Commands gestionarCarrera(HttpSession session) throws HipodromoException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-administrador.html"));
        }
        
        return null;

}





}
