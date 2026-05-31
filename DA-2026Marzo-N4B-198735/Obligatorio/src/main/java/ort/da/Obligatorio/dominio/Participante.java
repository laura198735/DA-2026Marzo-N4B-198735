package ort.da.Obligatorio.dominio;

import java.util.List;

import lombok.Getter;
@Getter
public class Participante {
    private Caballo caballo;
    private Carrera carrera;
    private Dividendo dividendo;
    private Estado estadoCarrera;
    private List<Apuesta> apuestas;

    public Participante(Caballo caballo, Carrera carrera, Dividendo dividendo) {
        this.caballo = caballo;
        this.carrera = carrera;
        this.dividendo = dividendo;
       // this.estadoCarrera = estadoCarrera;
    }


}