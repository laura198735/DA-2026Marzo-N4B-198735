package ort.da.Obligatorio.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apuesta {
    private double monto;
    private Modalidad modalidad;
    private Participante caballo; // El caballo al que se le apuesta
    private Carrera carrera;
    private boolean ganadora; // flag para marcar si la apuesta es ganadora
    private double comision;
    
    public Apuesta(double monto, Modalidad modalidad, Participante caballoApostado) {
        this.monto = monto;
        this.modalidad = modalidad;
        this.caballo = caballoApostado;
        this.carrera = caballoApostado.getCarrera();
        this.ganadora = false;
    }

    // Convenience method kept for compatibility with existing calls
    public boolean isGanadora() {
        return ganadora;
    }
}