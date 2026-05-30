package ort.da.Obligatorio.presentadores;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginJugadorPresentador {

    @PostMapping("/loginJugador")
    public Commands login(HttpSession session, CredencialDto credencialDto) {
        try {
            Credencial credencial = credencialDto.toCredencial();
            Usuario usuario = FachadaServicios.getInstancia().autenticar(credencial);
            session.setAttribute("usuarioLogueado", usuario);
            return Commands.create(new Command("redirigirJugador", "tableroJugador.html"));
            } catch (AutenticacionException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
    }

}