package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Finalizada implements IEstadoCarrera {

    public Finalizada() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede abrir una carrera que ya ha finalizado");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("La carrera ya está cerrada");
    }

    // Cambia el estado de la carrera a "Finalizada"
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws EstadoException {
        carrera.setEstadoCarrera(this);
    }
    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }
    @Override
    public String getNombreEstado() {
        return "Finalizada";
    }
}

