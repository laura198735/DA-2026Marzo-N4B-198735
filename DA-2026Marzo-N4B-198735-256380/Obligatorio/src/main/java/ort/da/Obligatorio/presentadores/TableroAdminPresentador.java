package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.excepciones.ObligatorioException;
import ort.da.Obligatorio.servicios.FachadaServicios;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tablero-administrador")
public class TableroAdminPresentador {

    public TableroAdminPresentador() {

    }


@GetMapping()
    public Commands mostrarTablero(HttpSession session) throws ObligatorioException {

        // 1. Recuperamos el usuario administrador guardado en sesión.
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        // 2. Pedimos los datos a la Fachada.
        Jornada jornadaActual = FachadaServicios.getInstancia().getJornadaActual();

        // 3. Devolvemos comandos claros para el frontend.
        return Commands.create(
                new Command("mostrarJornadaActual", jornadaActual));
    }   

    @PostMapping()
    public Commands cargarDatosTablero(HttpSession session) throws ObligatorioException {

        // 1. Recuperamos el usuario administrador guardado en sesión.
        Usuario usuarioAdministrador = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioAdministrador == null) {
            return Commands.create(
                    new Command("redirigirLogin", "/login-admin.html"));
        }

        // Pedimos los datos a la Fachada.
        double totalApostado = FachadaServicios.getInstancia().getTotalApostado();

        // 4. Devolvemos comandos claros para el frontend.
        return Commands.create(
                new Command("mostrarTotalApostado", totalApostado));
    }
}