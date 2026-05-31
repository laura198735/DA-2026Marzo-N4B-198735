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

    /// ****si no hay jornada actual devuelve la mas cercana anterior
    public Date obtenerFechaMasCercanaAnterior() {
        if (jornada.getfecha() != new Date()) {// Si no hay una jornada con fecha actual, se busca la fecha más cercana
                                               // anterior a la fecha actual
            Date fecha = new Date(); // Fecha actual
        }
        Date fechaMasCercana = null;
        for (Jornada jornada : jornadas) {
            if (jornada.getFecha().before(fecha)) {// Si la fecha de la jornada es anterior a la fecha actual
                if (fechaMasCercana == null || !jornada.getFecha().after(fechaMasCercana)) {// Si es nula o no hay una
                                                                                            // fecha cercana posterior.
                    fechaMasCercana = jornada.getFecha();// la fecha mas cercana se actualiza a la fecha de la jornada
                                                         // actual
                }
            }
        }
        return fechaMasCercana;

    }
//devuelve la jornada actual o la jornada con fecha más cercana anterior a la fecha actual
    public Jornada getJornadaActual() throws ObligatorioException {
        try {
            Date fechaMasCercanaAnterior = this.obtenerFechaMasCercanaAnterior();
            for (Jornada jornada : this.getJornadas()) {
                if (jornada.getFecha().equals(fechaMasCercanaAnterior)) {
                    return jornada;
                }
            }
            throw new ObligatorioException("No se encontró una jornada con la fecha más cercana anterior.");
        } catch (Exception e) {
            throw new ObligatorioException("Error al obtener la jornada actual: " + e.getMessage());
        }
    }

}
