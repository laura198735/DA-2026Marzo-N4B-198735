package ort.da.Obligatorio.servicios;

import java.util.List;

import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.excepciones.HipodromoException;

public class ServicioCarrera {

    private List<Carrera> carreras;
    private ServicioApuesta servicioApuesta;
    private ServicioJornada servicioJornada;

    public ServicioCarrera(ServicioJornada servicioJornada, ServicioApuesta servicioApuesta) {
        this.servicioJornada = servicioJornada;
        this.servicioApuesta = servicioApuesta;
    }

    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        try {
            carrera.getEstadoCarrera().abrirCarrera(carrera);

        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    public void cerrarCarrera(int numeroCarrera) throws HipodromoException {
        Carrera carrera = buscarCarrera(numeroCarrera);

        try {
            carrera.cerrarCarrera();

        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        try {
            carrera.finalizarCarrera(caballoGanador);
            servicioApuesta.pagarApuestasGanadoras(carrera);
        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    private Carrera buscarCarrera(int numeroCarrera) throws HipodromoException {
        return carreras.stream()
                .filter(carrera -> carrera.getNumeroCarrera() == numeroCarrera)
                .findFirst()
                .orElseThrow(() -> new HipodromoException("No existe la carrera " + numeroCarrera));
    }
}
