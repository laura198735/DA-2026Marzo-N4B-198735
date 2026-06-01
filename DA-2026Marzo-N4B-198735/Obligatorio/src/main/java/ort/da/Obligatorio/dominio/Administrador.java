package ort.da.Obligatorio.dominio;

import java.util.List;


public class Administrador extends Usuario {
    private List<Jornada> jornadas;
    
    public Administrador(String nombreUsuario, String password) {
        super(nombreUsuario, password);

    } 
    
    @Override
    public boolean validar(Credencial credencial) {
        return this.getNombreUsuario().equals(credencial.getNombre()) && this.getPassword().equals(credencial.getPassword());
    }

 
}
