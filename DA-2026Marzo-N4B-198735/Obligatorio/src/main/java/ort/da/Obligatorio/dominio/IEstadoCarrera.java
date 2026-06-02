package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public interface IEstadoCarrera {

    public void abrirCarrera(Carrera carrera) throws EstadoException;

    public void cerrarCarrera(Carrera carrera) throws EstadoException;

    public int asignarGanador(Carrera carrera) throws EstadoException;

    public boolean puedeApostar(Carrera carrera);
    
}
