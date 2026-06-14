package ort.da.Obligatorio.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Caballo {

    private int numero;
    private String nombre;

    public Caballo(String string, int i) {
        this.nombre = string;
        this.numero = i;
    }

  

}
