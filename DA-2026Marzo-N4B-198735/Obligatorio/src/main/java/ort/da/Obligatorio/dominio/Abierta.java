package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Abierta implements IEstadoCarrera {

    public Abierta() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("La carrera ya está abierta");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException(
                "No se puede cerrar una carrera en estado abierta. Primero debe cambiar a estado estable.");
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        throw new HipodromoException(
                "No se puede finalizar una carrera");
    }

    @Override    public boolean puedeApostar(Carrera carrera) {
        return true;
    }

    @Override
    public String getNombreEstado() {
        return "Abierta";
    }


    @Override
    public void actualizarEstadoPorDividendo(Carrera carrera) throws HipodromoException {
        if (carrera.todosLosDividendosSonValidos()) {
            carrera.setEstadoCarrera(new Estable());
        }
    }
}