package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.servicios.ServicioAutenticacion;

public class Administrador extends Usuario {

    
    public Administrador(String nombreUsuario, String password) {
        super(nombreUsuario, password);
    
    } 
    
    @Override
    public boolean validar(Credencial credencial) {
        return this.getNombreUsuario().equals(credencial.getNombre()) && this.getPassword().equals(credencial.getPassword());
    }

    @Override
    public Evento eventoConexion() {
        // El administrador notifica su propia clase de evento.
        return Evento.ADMINISTRADOR_CONECTADO;
    }

    @Override
    public void cerrarSesion(ServicioAutenticacion servicioAutenticacion) {
        // La propia clase sabe cómo cerrar su sesión: delega la eliminación
        // al servicio autenticador, sin necesidad de conocer el tipo exacto al
        // invocarlo desde afuera.
        if (servicioAutenticacion != null) {
            servicioAutenticacion.cerrarSesion(this);
        }
    }
  
}
