package ort.da.Obligatorio.dominio;

import lombok.Data;

@Data
public class Credencial {
    private String nombre;;
    private String password;

    public Credencial() {
    }

    public Credencial(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

}
