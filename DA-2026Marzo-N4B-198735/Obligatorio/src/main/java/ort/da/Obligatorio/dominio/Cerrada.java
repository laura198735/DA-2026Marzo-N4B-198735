package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Cerrada implements IEstadoCarrera {

    public Cerrada() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede abrir una carrera en estado cerrada");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("La carrera ya está cerrada");
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {

        if (carrera == null) {
            throw new HipodromoException("La carrera no existe.");
        }
        if (caballoGanador == null) {
            throw new HipodromoException("Debe seleccionar un caballo ganador.");
        }
        if (carrera.getRegistros() == null || carrera.getRegistros().isEmpty()) {
            throw new HipodromoException("No hay participantes en la carrera para finalizarla.");
        }

        Participante caballoParticipante = carrera.obtenerParticipanteEnCarrera(caballoGanador.getNumero());
        if (caballoParticipante == null) {
            throw new HipodromoException("El caballo ganador debe participar en la carrera.");
        }

        carrera.setCaballoGanador(caballoGanador);
        carrera.setEstadoCarrera(new Finalizada());
    }

    public void cambiarAEstadoEstable(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede cambiar a estado estable desde estado cerrada");
    }

    public void cambiarAEstadoAbierta(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede cambiar a estado abierta desde estado cerrada");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "Cerrada";
    }

    @Override
    public boolean estaFinalizada() {
        return false;
    }

    @Override
    public void actualizarEstadoPorDividendo(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede actualizar el estado de una carrera cerrada por dividendo");
    }
}
