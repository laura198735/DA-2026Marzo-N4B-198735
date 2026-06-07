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

    public Jornada getJornadaActual() {
        Date hoy = Jornada.truncarHora(new Date());

        for (Jornada jornada : jornadas) {
            if (jornada == null || jornada.getDia() == null)
                continue;
            Date diaJ = Jornada.truncarHora(jornada.getDia());
            if (diaJ == null)
                continue;
            // si la fecha de hoy es igual o posterior a la fecha de la jornada (solo fecha), entonces es la jornada actual
            if (!hoy.before(diaJ)) {
                return jornada;
            }
        }

        return null;
    }
    

    // las jornadas se agregan en orden cronologico inverso
    public void agregar(Jornada jornada) {
        this.jornadas.add(jornada);
    }

    // obtener el balance de la jornada actual: total apostado - total pagado
    public double getBalanceJornadaActual() {
        return this.getJornadaActual().getBalanceJornada();
    }

    public int getCantidadCarrerasJornada() {
        return this.getJornadaActual().getCantidadCarreras();
    }
}
