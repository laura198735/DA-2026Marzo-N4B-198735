package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Finalizada implements IEstadoCarrera {

    public Finalizada() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede abrir una carrera que ya ha finalizado");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("La carrera ya está cerrada");
    }

    // la carrera se finaliza en estado Cerrado.
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        throw new HipodromoException("La carrera ya se encuentra finalizada");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "Finalizada";
    }

    @Override
    public void actualizarEstadoPorDividendo(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede actualizar el estado de una carrera finalizada por dividendo");
    }
    @Override
    public boolean estaFinalizada() {
        return true;
    }
}
