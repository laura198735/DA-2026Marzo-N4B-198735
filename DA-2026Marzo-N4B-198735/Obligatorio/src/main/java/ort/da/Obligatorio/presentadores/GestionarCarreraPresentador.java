package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.ObligatorioException;

@Deprecated
@RequestMapping("/gestionar-carrera")
public class GestionarCarreraPresentador {

    public GestionarCarreraPresentador() {
    }

  @GetMapping()//maneja la carga inicial del formulario de login
    public Commands gestionarCarrera(HttpSession session) throws ObligatorioException {

        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-administrador.html"));
        }
        
        return null;

}




}
