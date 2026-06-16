package ort.da.Obligatorio.dominio;

import java.util.List;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Abierta implements IEstadoCarrera {

    Carrera carrera;
    List<Caballo> caballos;
    List<Participante> registrosParticipacion;

    public Abierta() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException("La carrera ya está abierta");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws HipodromoException {
        throw new HipodromoException(
                "No se puede cerrar una carrera en estado abierta. Primero debe cambiar a estado estable.");
    }

    @Override
    public boolean puedeApostar(Carrera carrera) {
        return true;
    }

    @Override
    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        throw new HipodromoException(
                "No se puede finalizar una carrera en estado abierta. Primero debe cerrar la carrera.");
    }

    public void cambiarAEstadoEstable(Carrera carrera) throws HipodromoException {
        if (!carrera.todosLosDividendosSonValidos()) {
            throw new HipodromoException(
                    "No se puede cambiar a estado estable si hay al menos un caballo con dividendo inválido");
        }
        carrera.setEstadoCarrera(new Estable()); // Cambia el estado de la carrera a "Estable"
    }

    @Override
    public String getNombreEstado() {
        return "Abierta";
    }

    @Override
    public boolean estaFinalizada() {
        return false;
    }

    @Override
public void actualizarEstadoPorDividendo(Carrera carrera) {
    if (carrera.todosLosDividendosSonValidos()) {
        carrera.setEstadoCarrera(new Estable());
    }
}
}