package ort.da.Obligatorio.dominio;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.Getter;

@Getter
public class Jornada {
    private int numero;
    private Date fecha;
    private List<Carrera> carreras;

    public Jornada() {
    }

    public Jornada(int numero, Date fecha) {
        this.numero = numero;
        this.fecha = fecha == null ? new Date() : fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    // recorre las carreras de la jornada y suma el total apostado en c/u
    public double getTotalApostado() {
        double total = 0.0;
        List<Carrera> carreras = getCarreras();
        if (carreras == null)
            return 0.0;
        for (Carrera carrera : carreras) {
            if (carrera != null) {
                total += carrera.getTotalApostado();
            }
        }
        return total;
    }
    public double getTotalPagado() {
        double total = 0.0;
        List<Carrera> carreras = getCarreras();
        if (carreras == null)
            return 0.0;
        for (Carrera carrera : carreras) {
            if (carrera != null) {
                total += carrera.getTotalPagado();
            }
        }
        return total;
    }   

}