package ort.da.Obligatorio.dominio;

import java.util.List;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Abierta implements IEstadoCarrera {

    Carrera carrera;
    List<Caballo> caballos;
    List<Participante> registrosParticipacion;

    private List<Apuesta> apuestas;

    public Abierta() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("La carrera ya está abierta"); // Implementación específica para la clase Abierta
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede cerrar una carrera en estado abierta");
    }

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede asignar un ganador a una carrera en estado abierta");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        carrera.setEstadoCarrera(this);// cambia el estado de la carrera a Abierta y permite apostar
        return true;//**ver si pasa a abierta */
    }

}
