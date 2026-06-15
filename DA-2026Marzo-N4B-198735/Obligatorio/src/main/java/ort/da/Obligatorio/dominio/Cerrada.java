package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;
import ort.da.Obligatorio.excepciones.HipodromoException;

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

    
    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws  HipodromoException {

         if(carrera ==null){
            throw new HipodromoException("La carrera no existe.");
        }
        if (caballoGanador == null ) {
            throw new HipodromoException("Debe seleccionar un caballo ganador.");
        }
        if (carrera.getRegistros() == null || carrera.getRegistros().isEmpty()) {
            throw new HipodromoException("No hay participantes en la carrera para finalizarla.");
        }       
        Participante caballoParticipante = carrera.obtenerParticanteEnCarrera(caballoGanador.getNumero());
        if (caballoParticipante == null) {
            throw new HipodromoException("El caballo ganador debe participar en la carrera.");
        }

        carrera.setCaballoGanador(caballoGanador);
        carrera.setEstadoCarrera(new Finalizada());
    }

    @Override
    public String getNombreEstado() {
        return "Cerrada";
    }
}
