package ort.da.Obligatorio.dominio;

import lombok.Getter;
import ort.da.Obligatorio.excepciones.AutenticacionException;
import ort.da.Obligatorio.servicios.ServicioAutenticacion;

public abstract class Usuario { // Inyección de dependencia

    @Getter
    private String nombreUsuario;
    @Getter
    private String password;
    @Getter
    private String nombre;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
      
    }

    public abstract boolean validar(Credencial credencial);



}
