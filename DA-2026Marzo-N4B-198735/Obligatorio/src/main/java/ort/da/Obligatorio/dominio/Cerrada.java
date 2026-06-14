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
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

    //
    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws EstadoException {
        if (caballoGanador == null) {
            throw new EstadoException("Debe seleccionar un caballo ganador.");
        }
        Caballo caballoParticipante = carrera.buscarCaballoParticipante(caballoGanador.getNumero());
        if (caballoParticipante == null) {
            throw new EstadoException("El caballo ganador debe participar en la carrera.");
        }

        carrera.setCaballoGanador(caballoGanador);
        carrera.setEstadoCarrera(new Finalizada());
    }

    @Override
    public String getNombreEstado() {
        return "Cerrada";
    }
}
