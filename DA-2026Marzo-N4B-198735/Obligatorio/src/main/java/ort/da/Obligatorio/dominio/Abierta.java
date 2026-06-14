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
        carrera.setEstadoCarrera(this); // Cambia el estado de la carrera a "Abierta"
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede cerrar una carrera en estado abierta");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        carrera.setEstadoCarrera(this);// cambia el estado de la carrera a Abierta y permite apostar
        return true;// **ver si pasa a abierta */
    }

    public void cambiarAEstadoEstable(Carrera carrera) throws EstadoException {
        if (!Participante.tieneDividendoValido(carrera)) {
            throw new EstadoException(
                    "No se puede cambiar a estado estable si hay al menos un caballo con dividendo inválido");
        }
        carrera.setEstadoCarrera(new Estable()); // Cambia el estado de la carrera a "Estable"
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws EstadoException {
        throw new EstadoException("No se puede finalizar una carrera en estado abierta");
    }

    @Override
    public String getNombreEstado() {
     return "Abierta";
}
}