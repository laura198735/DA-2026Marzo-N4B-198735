package ort.da.Obligatorio.dominio;

import lombok.Getter;

public class Apuesta {
    // necesito Jugador? una lista de jugadores
    // id de Apuesta?
    @Getter
    private double monto;
    private Modalidad modalidad;
    private Participante caballo; // El caballo al que se le apuesta
    private Carrera carrera;

    public Apuesta(double monto, Modalidad modalidad, Participante caballoApostado) {
        this.monto = monto;
        this.modalidad = modalidad;
        this.caballo = caballoApostado;
        this.carrera = caballoApostado.getCarrera();
    }

    public Object getCaballo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCaballo'");
    }

}