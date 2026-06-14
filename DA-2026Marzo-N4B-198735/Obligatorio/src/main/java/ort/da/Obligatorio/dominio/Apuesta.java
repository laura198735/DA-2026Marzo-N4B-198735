package ort.da.Obligatorio.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apuesta {
    private double monto;
    private Jugador jugador;
    private Modalidad modalidad;
    private Participante participante; // El caballo al que se le apuesta

    public Apuesta(double monto, Modalidad modalidad, Participante participante) {
        this.monto = monto;
        this.modalidad = modalidad;
        this.participante = participante;
        this.jugador = null; // El jugador se asigna cuando se realice la apuesta a través del método realizarApuesta del jugador
    }

    public boolean esApuestaGanadora() {
        return participante != null && participante.esCaballoGanador();

    }
//para el hipodromo  
    public double calcularGanancia() {
        if (esApuestaGanadora()) {
            double valorDividendo = participante.getDividendoFinal();
            return monto * valorDividendo;
        }
        return 0.0; // Si la apuesta no es ganadora, no se gana nada
    }

}
