package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Estable implements IEstadoCarrera {
/**  Todos los caballos participantes tienen dividendo válido. 
     Se pueden realizar apuestas para la carrera. 
    Debe volver a estado Abierta si hay al menos 1 caballo con dividendo invalido. 
    Se puede cerrar la carrera. */
    public Estable() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede abrir una carrera en estado estable"); 
    }

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede asignar un ganador a una carrera en estado estable");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
            throw new EstadoException("No se puede cerrar una carrera en estado estable"); 
        
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return true;
    }




}
