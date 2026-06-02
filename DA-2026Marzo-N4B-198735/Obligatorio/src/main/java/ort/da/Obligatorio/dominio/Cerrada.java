package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Cerrada implements IEstadoCarrera {

    public Cerrada() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede abrir una carrera en estado cerrada");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("La carrera ya está cerrada");
    }

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        Caballo ganador = carrera.getCaballoGanador();
        if (ganador == null) {
            throw new EstadoException("No se puede asignar un ganador a una carrera sin ganador definido");
        }
        return ganador.getNumero();

    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

}
