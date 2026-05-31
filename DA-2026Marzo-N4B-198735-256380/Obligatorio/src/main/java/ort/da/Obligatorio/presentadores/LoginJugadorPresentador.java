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
@RequestMapping("/login-jugador")//ruta para acceder al login del jugador
public class LoginJugadorPresentador {
  
    public LoginJugadorPresentador() {
    }

    @GetMapping()
    public Commands mostrarTablero(HttpSession session) throws ObligatorioException {
        //recupera el jugador guardado en sesion
                Usuario usuarioJugador = (Usuario) session.getAttribute("usuarioLogueado");
        //si no hay jugador en sesion, redirige al login
        if (usuarioJugador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-jugador.html"));
        }


        Jornada jornadaActual = FachadaServicios.getInstancia().getJornadaActual();

        return Commands.create(
                new Command("mostrarJornadaActual", jornadaActual));
    }

    @PostMapping()
    public Commands cargarDatosTablero(HttpSession session) throws ObligatorioException {

        Usuario usuarioJugador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioJugador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-jugador.html"));
        }

           //ver que contiene el tablero
    double totalApostado = FachadaServicios.getInstancia().getTotalApostado();

        
        return Commands.create(
                new Command("mostrarTotalApostado", totalApostado));
    }
}