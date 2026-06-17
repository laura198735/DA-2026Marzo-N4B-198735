package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Definida implements IEstadoCarrera {

    @Override
    public void abrirCarrera(Carrera carrera) {
        carrera.invalidarDividendosParticipantes(); // Invalida los dividendos de los participantes al abrir la carrera
        carrera.setEstadoCarrera(new Abierta()); // Cambia el estado de la carrera a "Abierta"

    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede cerrar una carrera en estado definido");
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        throw new HipodromoException("No se puede asignar un ganador a una carrera en estado Definida");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "Definida";
    }

     @Override
    public void actualizarEstadoPorDividendo(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("No se puede actualizar el estado de una carrera definida por dividendo");
    }
}
