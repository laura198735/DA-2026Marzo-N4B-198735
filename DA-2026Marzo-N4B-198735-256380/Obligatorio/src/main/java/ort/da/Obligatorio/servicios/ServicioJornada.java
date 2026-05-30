package ort.da.Obligatorio.servicios;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.Obligatorio.dominio.Jornada;

@Getter
public class ServicioJornada {

    private List<Jornada> jornadas;

    // Lista de jornadas para gestión de carreras
    public ServicioJornada() {
        this.jornadas = new ArrayList<>();
    }

    public List<Jornada> getJornadas() {
        return jornadas;
    }
    ///****VER
    public Date obtenerFechaMasCercanaAnterior() {
        Date fecha = new Date(); // Fecha actual);
        Date fechaMasCercana = null;
        for (Jornada jornada : jornadas) {
            if (jornada.getFecha().before(fecha)) {// Si la fecha de la jornada es anterior a la fecha actual
                if (fechaMasCercana == null || jornada.getFecha().after(fechaMasCercana)) {// Si es nula o no hay una fecha cercana posterior.
                    fechaMasCercana = fecha;// la fecha mas cercana se actualiza a la fecha de la jornada actual
                }
                fechaMasCercana = jornada.getFecha();// Si la fecha de la jornada es anterior a hoy, 
                // =>se actualiza la fecha más cercana de la lista
            }
        }
        return fechaMasCercana;

    }

}
