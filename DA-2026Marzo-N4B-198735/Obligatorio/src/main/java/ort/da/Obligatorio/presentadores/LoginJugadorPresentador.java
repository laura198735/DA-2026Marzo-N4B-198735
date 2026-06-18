package ort.da.Obligatorio.presentadores;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("session") // Mantener el estado del usuario logueado en la sesión
public class LoginJugadorPresentador {

    public LoginJugadorPresentador() {
    }

    @PostMapping("/login-jugador")
    public Commands login(HttpSession session, @ModelAttribute CredencialDto credencialDto) {
        try {
            Credencial credencial = credencialDto.toCredencial();
            System.out.println("Nombre recibido: " + credencial.getNombre());
            System.out.println("Password recibido: " + credencial.getPassword());

            Login loginJugador = FachadaServicios.getInstancia().autenticarJugador(credencial);
            Jugador jugador = (Jugador) loginJugador.getUsuario();
            session.removeAttribute("administradorLogueado");//elimino atributo de logueo previo
            session.setAttribute("jugadorLogueado", jugador);

            System.out.println("Nombre recibido: " + jugador.getNombreUsuario());
            return Commands.create(new Command("redirigir-tablero-jugador", "/tablero-jugador.html"));
        } catch (AutenticacionException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
    }



    @PostMapping("/logout-jugador")
    public Commands logout(HttpSession session) {
        Jugador jugador = AuxiliarSesion.obtenerJugadorLogueado(session);
        FachadaServicios.getInstancia().cerrarSesionJugador(jugador);
        session.removeAttribute("jugadorLogueado");
        return Commands.create(new Command("redirigirLogin", "/login-jugador.html"));
    }
}
