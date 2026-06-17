package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.excepciones.HipodromoException;

public class ServicioApuesta {
    List<Apuesta> apuestas;

    public ServicioApuesta() {
        this.apuestas = new ArrayList<>();
    }

    public ServicioApuesta(List<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }

    public void confirmarApuesta(Apuesta apuesta) {
        if (apuesta == null) {
            return;
        }
        apuestas.add(apuesta);
    }

    public void pagarApuestasGanadoras(Carrera carrera) throws HipodromoException {
        if (carrera == null || carrera.getRegistros() == null) {
            return;
        }

        for (Participante participante : carrera.getRegistros()) {
            if (participante == null || participante.getApuestas() == null) {
                continue;
            }

            for (Apuesta apuesta : participante.getApuestas()) {
                if (apuesta == null || apuesta.getJugador() == null || !apuesta.esApuestaGanadora()) {
                    continue;
                }

                double pago = apuesta.calcularGanancia();
                apuesta.getJugador().setSaldo(apuesta.getJugador().getSaldo() + pago);
            }
        }
    }
}
