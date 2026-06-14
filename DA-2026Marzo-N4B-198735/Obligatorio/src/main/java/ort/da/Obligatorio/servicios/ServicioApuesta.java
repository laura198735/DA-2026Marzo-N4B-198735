package ort.da.Obligatorio.servicios;

import java.util.List;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Carrera;

public class ServicioApuesta {
    List<Apuesta> apuestas;

    public ServicioApuesta() {
    }

    public ServicioApuesta(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }

    public void confirmarApuesta(Apuesta apuesta) {
        apuestas.add(apuesta);
    }

    public void pagarApuestasGanadoras(Carrera carrera) {
        for (Apuesta apuesta : apuestas) {
            if (apuesta.getParticipante().getCarrera().equals(carrera) &&
                    apuesta.getParticipante().getCaballo().equals(carrera.getCaballoGanador())) {

                double pago = apuesta.getMonto() * carrera.getDividendoFinalGanador();
                apuesta.getJugador().setSaldo(apuesta.getJugador().getSaldo() + pago);
            }
        }
    }
}
