package ort.da.Obligatorio.dominio;

import java.util.Date;
import java.util.List;

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
        this.fecha = fecha == null ? new Date(System.currentTimeMillis()) : fecha;// Si no hay  una fecha, 
        // se asigna la fecha más próxima anterior a la fecha actual 
    }

    public Date getFecha() {
        return fecha;
    }
    //recorre las carreras de la jornada y suma el total apostado en c/u 
    public double getTotalApostado() { 
        double total = 0.0;
        for (Carrera carrera : getCarreras()) {
            total += carrera.getTotalApostado();
        }
        return total;
    }

    private List<Carrera> getCarreras() {
        return carreras;
    }



}