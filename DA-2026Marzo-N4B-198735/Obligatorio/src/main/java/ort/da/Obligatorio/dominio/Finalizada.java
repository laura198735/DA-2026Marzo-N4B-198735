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
        throw new EstadoException("La carrera ya está cerrada y finalizada");
    }

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede asignar un ganador a una carrera que ya ha finalizado");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

}
