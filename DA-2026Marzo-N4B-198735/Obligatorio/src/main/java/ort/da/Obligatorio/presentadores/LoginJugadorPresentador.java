package ort.da.Obligatorio.presentadores;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Credencial;

import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dtos.CredencialDto;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-jugador") // ruta para acceder al login del jugador
@Scope("session") // Mantener el estado del usuario logueado en la sesión
public class LoginJugadorPresentador {

    public LoginJugadorPresentador() {
    }

    @PostMapping()
    public Commands login(HttpSession session, @ModelAttribute CredencialDto credencialDto) {
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



}