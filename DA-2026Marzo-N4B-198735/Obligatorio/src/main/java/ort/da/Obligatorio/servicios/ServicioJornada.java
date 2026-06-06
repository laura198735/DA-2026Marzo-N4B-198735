package ort.da.Obligatorio.servicios;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.excepciones.HipodromoException;

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
        Date fecha = new Date(); // Fecha actual
        Date fechaMasCercana = null;
        for (Jornada j : jornadas) {
            if (j == null || j.getFecha() == null) continue;
            if (j.getFecha().before(fecha)) { // Si la fecha de la jornada es anterior a la fecha actual
                if (fechaMasCercana == null || j.getFecha().after(fechaMasCercana)) {
                    fechaMasCercana = j.getFecha(); // actualiza la fecha mas cercana anterior
                }
            }
        }
        return fechaMasCercana;

    }
    
    // Si no hay una fecha,
    // se asigna la fecha más próxima anterior a la fecha actual
//devuelve la jornada actual o la jornada con fecha anterior más cercana a la fecha actual
    public Jornada getJornadaActual() throws HipodromoException {
        try {
            Date fechaMasCercanaAnterior = this.obtenerFechaMasCercanaAnterior();
            for (Jornada jornada : this.getJornadas()) {
                if (jornada.getFecha().equals(fechaMasCercanaAnterior)) {
                    return jornada;
                }
            }
            throw new HipodromoException("No se encontró una jornada con la fecha más cercana anterior.");
        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la jornada actual: " + e.getMessage());
        }
    }
    //obtener el balance de la jornada actual: total apostado - total pagado
    public double getBalanceJornadaActual() throws HipodromoException {
        return this.getJornadaActual().getBalanceJornada();
    }


}
