package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Estable implements IEstadoCarrera {
    /**
     * Todos los caballos participantes tienen dividendo válido.
     * Se pueden realizar apuestas para la carrera.
     * Debe volver a estado Abierta si hay al menos 1 caballo con dividendo
     * invalido.
     * Se puede cerrar la carrera.
     */
    public Estable() {
    }


    @Override
    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede abrir una carrera en estado estable");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        if (carrera == null) {
            throw new HipodromoException("La carrera no existe.");
        }
        if (carrera.getRegistros() == null || carrera.getRegistros().isEmpty()) {
            throw new HipodromoException("No hay participantes en la carrera para cerrarla.");
        }
        carrera.setEstadoCarrera(new Cerrada()); // Cambia el estado de la carrera a "Cerrada"
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        throw new HipodromoException("No se puede asignar un ganador a una carrera en estado estable");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return true;
    }

    @Override
    public String getNombreEstado() {
        return "Estable";
    }

    @Override
    public boolean estaFinalizada() {
        return false;
    }

    @Override
    public void actualizarEstadoPorDividendo(Carrera carrera) {
        if (carrera == null || carrera.getRegistros() == null) {
            return; // No hay registros de participación
        }
        for (Participante participante : carrera.getRegistros()) {
            if (participante != null && !participante.tieneDividendoValido()) {
                carrera.setEstadoCarrera(new Abierta()); // Cambia el estado de la carrera a "Abierta"
                break; // No es necesario seguir verificando
            }
        }
    }
}