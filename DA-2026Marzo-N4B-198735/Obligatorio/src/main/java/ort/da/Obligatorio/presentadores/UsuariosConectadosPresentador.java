package ort.da.Obligatorio.presentadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Login;
import ort.da.Obligatorio.dtos.LoginDto;
import ort.da.Obligatorio.observer.Observable;
import ort.da.Obligatorio.observer.Observador;

import ort.da.Obligatorio.servicios.FachadaServicios;
import ort.da.Obligatorio.servicios.SistemaAcceso;

@RestController
@Scope("session")
public class UsuariosConectadosPresentador implements Observador {

    private ConexionNavegador conexionNavegador;
    private Administrador usuarioLogueado;
    private HttpSession session;

    public UsuariosConectadosPresentador(@Autowired ConexionNavegador conexionNavegador, HttpSession session) {
        this.conexionNavegador = conexionNavegador;
        this.session = session;
        SistemaAcceso.getInstancia().subscribir(this);
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE(HttpSession session) {
        this.session = session;
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/usuariosConectados")
    public Commands mostrarUsuariosConectados(
            @SessionAttribute(name = "administradorLogueado", required = false) Administrador usuarioAdministrador) {
        this.usuarioLogueado = usuarioAdministrador;
        return Commands.create(generarCommandsDeLogines(),
                new Command("nombreAdministrador", usuarioLogueado.getNombreUsuario()));
    }

    private Command generarCommandsDeLogines() {
        List<Login> loginesDominio = FachadaServicios.getInstancia().getLogins();
        List<LoginDto> logines = LoginDto.fromList(loginesDominio);
        return new Command("usuariosConectados", logines);
    }

    @Override
    public void actualizar(Observable origen, Object evento) {//SOLO ADMINISTRADOR CONECTADO
        if (origen instanceof SistemaAcceso
                && (evento == Observable.Evento.ADMINISTRADOR_CONECTADO)
                  ) {
            conexionNavegador.enviarCommands(Commands.create(generarCommandsDeLogines()));
        }
    }
}
