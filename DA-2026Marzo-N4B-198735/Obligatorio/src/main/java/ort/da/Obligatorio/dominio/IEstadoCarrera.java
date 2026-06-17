package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

//patron state: (cada estado de la carrera modifica su comporatmiento)
public interface IEstadoCarrera {

    public void abrirCarrera(Carrera carrera) throws HipodromoException;

    public void cerrarCarrera(Carrera carrera) throws HipodromoException;

    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws HipodromoException;

    public boolean puedeApostar(Carrera carrera);

    String getNombreEstado();

    default boolean estaFinalizada() {
        return false;
    }

    default boolean estaCerrada() {
        return false;
    }

    public void actualizarEstadoPorDividendo(Carrera carrera) throws HipodromoException;;

}
