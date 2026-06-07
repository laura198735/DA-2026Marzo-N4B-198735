package ort.da.Obligatorio.dominio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Jornada {
    private int numero;
    private Date dia;
    private List<Carrera> carreras;

    public Jornada() {
    }

    public Jornada(int numero, Date fecha) {
        this.numero = numero;
        this.dia = fecha == null ? new Date() : fecha;

    }

    public Jornada(Date dia) {// constructor para crear la jornada actual
        this.dia = truncarHora(dia);
    }

    // truncarle la hora asi se puede comparar mas facil
        public static Date truncarHora(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
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

    public double getTotalComisiones() {
        double total = 0.0;
        List<Carrera> carreras = getCarreras();
        if (carreras == null)
            return 0.0;
        for (Participante participante : carreras.stream()
                .flatMap(c -> c.getRegistros().stream()).toList()) {
            if (participante == null)
                continue;
            List<Apuesta> apuestas = participante.getApuestas();
            if (apuestas == null)
                continue;
            for (Apuesta ap : apuestas) {
                if (ap != null)
                    total += ap.getComision();
            }
        }
        return total;
    }

    public double getBalanceJornada() {
        return getTotalApostado() - getTotalPagado();
    }

    public int getCantidadCarreras() {
        List<Carrera> carreras = getCarreras();
        return carreras == null ? 0 : carreras.size();
    }
}