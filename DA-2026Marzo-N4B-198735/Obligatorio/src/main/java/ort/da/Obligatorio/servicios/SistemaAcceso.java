package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.observer.Observable;

public class SistemaAcceso extends Observable {

    private static SistemaAcceso instancia;
    private Usuario usuarioLogueado;
    public List<Usuario> administradoresConectados = new ArrayList<>();

    private SistemaAcceso() {
        // Constructor privado para evitar instanciación externa 
    }

    public static SistemaAcceso getInstancia() {
        if (instancia == null) {
            instancia = new SistemaAcceso();
        }
        return instancia;
    }
    //deja conectar al adminstrador si no hay un Administrador conectado.
    public void agregarAdministradorConectado(Usuario administrador) {
        if (!administradoresConectados.contains(administrador)) {
            administradoresConectados.add(administrador);
            this.notificar(Evento.ADMINISTRADOR_CONECTADO);
        }
    }

    public void quitarAdministradorConectado(Usuario administrador) {
        if (administradoresConectados.remove(administrador)) {
            this.notificar(Evento.ADMINISTRADOR_CONECTADO);// Notificar que un administrador se ha desconectado
        }
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        notificarUsuario(usuario);
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void notificarAdministradorConectado() {
        this.notificar(Evento.ADMINISTRADOR_CONECTADO);
    }

    public void notificarJugadorConectado() {
        this.notificar(Evento.JUGADOR_CONECTADO);
    }

    private void notificarUsuario(Usuario usuario) {
        // Delegamos la decision al propio usuario para evitar instanceof.
        // Si se agrega otro tipo de usuario, este metodo no necesita cambiar.
        if (usuario != null) {
            this.notificar(usuario.eventoConexion());
        }
    }

}
