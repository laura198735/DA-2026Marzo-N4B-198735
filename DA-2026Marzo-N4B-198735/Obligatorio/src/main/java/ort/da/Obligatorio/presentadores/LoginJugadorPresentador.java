package ort.da.Obligatorio.presentadores;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.excepciones.ObligatorioException;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-jugador") // ruta para acceder al login del jugador
public class LoginJugadorPresentador {

    public LoginJugadorPresentador() {
    }

     @PostMapping()
    public Commands login(HttpSession session, CredencialDto credencialDto) {
        try {
            Credencial credencial = credencialDto.toCredencial();
            System.out.println("Nombre recibido: " + credencial.getNombre());
            System.out.println("Password recibido: " + credencial.getPassword());

            Usuario jugador = FachadaServicios.getInstancia().autenticar(credencial);
            session.setAttribute("usuarioLogueado", jugador);

            System.out.println("Nombre recibido: " + jugador.getNombre());
            return Commands.create(new Command("redirigir-tablero-jugador", "/tablero-jugador.html"));
        } catch (AutenticacionException e) {
            return Commands.create(new Command("error", e.getMessage()));
        }
    }

    @GetMapping()
    public Commands cargarDatosTablero(HttpSession session) throws ObligatorioException {

        Usuario usuarioJugador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioJugador == null) {
            return Commands.create(
                    new Command("redirigir-login-jugador", "/login-jugador.html"));
        }

        return Commands.create(new Command("redirigir-tablero-jugador", "/tablero-jugador.html"));
        // ver que contiene el tablero
        double totalApostado = FachadaServicios.getInstancia().getTotalApostado();

        return Commands.create(
                new Command("mostrarTotalApostado", totalApostado));
    }

    @PostMapping("/logout")
    public Commands logout(HttpSession session) {
        FachadaServicios.getInstancia().logout((Login) session.getAttribute("loginUsuario"));
        session.removeAttribute("loginUsuario");
        session.invalidate();
        return Commands.create(new Command("redirigir", "login.html"));
    }

}