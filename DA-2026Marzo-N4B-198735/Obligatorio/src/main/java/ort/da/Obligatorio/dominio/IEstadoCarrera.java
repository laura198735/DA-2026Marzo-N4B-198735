package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;
import ort.da.Obligatorio.excepciones.HipodromoException;
//patron state: (cada estado de la carrera modifica su comporatmiento)
public interface IEstadoCarrera {

    public void abrirCarrera(Carrera carrera) throws EstadoException;

    public void cerrarCarrera(Carrera carrera) throws EstadoException;

    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws EstadoException, HipodromoException;
     
    
    public boolean puedeApostar(Carrera carrera);

    String getNombreEstado();
}
