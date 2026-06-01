package ort.da.Obligatorio.dominio;

import lombok.Getter;

@Getter
public class Comision {

    double porcentaje; // porcentaje de comisión sobre el total apostado en la carrera  
    public Comision() {
    }

    public Comision(double porcentaje) {
        this.porcentaje = porcentaje;
    }


    
}
