package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.IModalidad;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.dominio.Simple;
import ort.da.Obligatorio.dominio.Super;
import ort.da.Obligatorio.dominio.Triple;

@Getter
public class ServicioApuesta {
   
    private List<Apuesta> apuestas = new ArrayList<>();
    private IModalidad modalidad;

  
    public void confirmarApuesta(Apuesta apuesta) {
        if (apuesta == null) {
            return;
        }
        apuestas.add(apuesta);
    }

    public List<Apuesta> getApuestasCarrera(Carrera carrera) throws HipodromoException {
        if (carrera == null) {
            throw new HipodromoException("Carrera no puede ser nula.");
        }
        List<Apuesta> apuestasCarrera = new ArrayList<>();
        for (Participante participante : carrera.getRegistros()) {
            if (participante == null || participante.getApuestas() == null) {
                continue;
            }
            apuestasCarrera.addAll(participante.getApuestas());
        }
        return apuestasCarrera;
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
    public List<IModalidad> getModalidadesDisponibles() {
        // Aquí se podrían agregar más modalidades según sea necesario
        return List.of(
                new Simple(),
                new Super(),
                new Triple()
        );
    }

    public Apuesta buscarApuestaPorNumero(Carrera carrera, int numeroApuesta) {
        if (carrera == null || carrera.getRegistros() == null) {
            return null;
        }

        for (Participante participante : carrera.getRegistros()) {
            if (participante == null || participante.getApuestas() == null) {
                continue;
            }

            for (Apuesta apuesta : participante.getApuestas()) {
                if (apuesta != null && apuesta.getId() == numeroApuesta) {
                    return apuesta;
                }
            }
        }
        return null; // Si no se encuentra la apuesta, se devuelve null
    }

    public IModalidad buscarModalidadPorNumeroApuesta(Carrera carrera, int numeroApuesta) {
        Apuesta apuesta = buscarApuestaPorNumero(carrera, numeroApuesta);
        return apuesta == null ? null : apuesta.getModalidad();
    }
}
