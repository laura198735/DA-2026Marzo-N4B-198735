package ort.da.Obligatorio.dominio;

import lombok.Getter;
import lombok.Setter;
import ort.da.Obligatorio.observer.Observable;
import ort.da.Obligatorio.servicios.ServicioAutenticacion;

/**
 * Clase abstracta que representa a un usuario del sistema.
 */
public abstract class Usuario extends Observable{
    @Getter
    private String nombreUsuario;
    @Getter
    private String password;
    @Getter
    @Setter
    private String nombre;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
       
    }

    public abstract boolean validar(Credencial credencial);

    // Cada subtipo define que evento representa su conexion.
    // Asi evitamos preguntar el tipo con instanceof desde los servicios.
    public abstract Evento eventoConexion();

    // Cada tipo concreto resuelve su cierre de sesión según su propia lógica.
    // Esto permite que el servicio invoque el comportamiento correcto sin
    // depender de instanceof ni de if/else por tipo de usuario.
    public abstract void cerrarSesion(ServicioAutenticacion servicioAutenticacion);

}
