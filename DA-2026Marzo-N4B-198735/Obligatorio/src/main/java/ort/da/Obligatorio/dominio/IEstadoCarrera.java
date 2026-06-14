package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;
//patron state: (cada estado de la carrera modifica su comporatmiento)
public interface IEstadoCarrera {

    public void abrirCarrera(Carrera carrera) throws EstadoException;

    public void cerrarCarrera(Carrera carrera) throws EstadoException;

    public void finalizarCarrera(Carrera carrera, Caballo caballoGanador) throws EstadoException;
     
    
    public boolean puedeApostar(Carrera carrera);

    String getNombreEstado();
}
