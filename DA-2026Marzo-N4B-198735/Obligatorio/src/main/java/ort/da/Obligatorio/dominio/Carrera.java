package ort.da.Obligatorio.dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ort.da.Obligatorio.excepciones.ObligatorioException;

@Getter
@Setter
public class Carrera {

    private int numero;
    private String nombre;
    private Estado estado;

    // Lista de registros y jornada correspondiente
    private List<Participante> registros;
    private Jornada jornada;

    public Carrera() {

    }

    public Carrera(String nombre, Date fecha, double monto, Estado estado) {
        this.nombre = nombre;
        this.estado = estado;
        this.registros = new ArrayList<>();
        this.jornada = new Jornada();
        
    }

    public boolean esHabilitada(Caballo caballo) throws ObligatorioException {
        if (registros == null || registros.isEmpty()) {
            throw new ObligatorioException("La carrera no está habilitada para apostar.");
        }
        for (Participante registro : registros) {
            if (esCaballoDelRegistro(registro, caballo)) { // && registro.getEstadoCarrera() == EstadoCarrera.HABILITADA)
                return true; // El caballo está habilitado para apostar
            }
        }

        throw new ObligatorioException("La carrera no está habilitada para apostar.");
    }

    public boolean esCaballoDelRegistro(Participante registro, Caballo caballo) {
        Caballo caballoRegistro = registro.getCaballo();
        return caballo.equals(caballoRegistro);
    }

    public double getTotalApostado() {
        double total = 0.0;
        for (Participante registro : registros) {
            for (Apuesta apuesta : registro.getApuestas()) {
                total += apuesta.getMonto();
            }
        }
        return total;
    }

    public boolean esFinalizada() {
        if (registros == null || registros.isEmpty()) {
            return false;
        }
        for (Participante registro : registros) {
            if (registro.getEstadoCarrera() == null) {
                return false; // Si algún registro no tiene estado, consideramos no finalizada
            }
        }
        return true; // Todos los registros tienen estado, asumimos finalizada
    }

    public double getTotalPagado() {
        double total = 0.0;
        for (Participante registro : registros) {
            for (Apuesta apuesta : registro.getApuestas()) {
                if (apuesta.isGanadora()) {
                    double valorDividendo = registro.getDividendo() == null ? 0.0 : registro.getDividendo().getValor();
                    total += apuesta.getMonto() * valorDividendo; // Se paga el monto apostado multiplicado por el valor del dividendo
                }
            }
        }
        return total;
    }
}