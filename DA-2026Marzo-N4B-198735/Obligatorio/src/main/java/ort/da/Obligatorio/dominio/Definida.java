package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Definida implements IEstadoCarrera {

    @Override
    public void abrirCarrera(Carrera carrera) {
        carrera.setEstadoCarrera(new Abierta()); // Cambia el estado de la carrera a "Abierta"
    
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException  {
        throw new EstadoException("No se puede cerrar una carrera en estado definido");}

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede asignar un ganador a una carrera en estado Definido");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return false;
    }

}
