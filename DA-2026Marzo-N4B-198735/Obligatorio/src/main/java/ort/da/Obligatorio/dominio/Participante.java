package ort.da.Obligatorio.dominio;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Participante {
    private Caballo caballo;
    private Carrera carrera;
    private double dividendoActual;
    private double dividendoFinal;
    private List<Apuesta> apuestas;

    public Participante(Caballo caballo, Carrera carrera) {
        this.caballo = caballo;
        this.carrera = carrera;
        this.apuestas = new ArrayList<Apuesta>();
        this.dividendoActual = 0.0;
        this.dividendoFinal = 0.0;
    }

    // Verifico si el caballo es el que participa por el numero de caballo para
    // saber si corrió
    public boolean esCaballoDelRegistro(Participante registro, Caballo caballo) {
        return registro != null
                && registro.getCaballo() != null
                && caballo != null
                && registro.getCaballo().getNumero() == caballo.getNumero();
    }

    public boolean esCaballoGanador() {
        return carrera != null
                && caballo != null
                && carrera.getCaballoGanador() != null
                && caballo.getNumero() == carrera.getCaballoGanador().getNumero();
    }

    public double getTotalApostadoAlCaballo() {
        double total = 0.0;

        for (Apuesta apuesta : apuestas) {
            total += apuesta.getMonto();
        }

        return total;
    }

    /**
     * Un dividendo es válido únicamente cuando:
     * La cantidad de apuestas al caballo es mayor a 0.
     * El dividendo es mayor a 1.
     * 
     * l dividendo es un número decimal que se calcula con la siguiente fórmula:
     * Dividendo = (total apostado en la carrera - comisión) / total apostado al
     * caballo
     * dividendo = total apostado en la carrera luego de restada la comisión / total
     * apostado
     * al caballo
     */

    /*** se tiene que calcular cada vez que se hace una apuesta */
    public double calcularDividendo() {
    if (carrera == null) {
        return 0.0;
    }

    double totalApostadoEnCarrera = carrera.getTotalApostado();
    double totalApostadoAlCaballo = getTotalApostadoAlCaballo();

    if (totalApostadoAlCaballo <= 0) {
        return 0.0;
    }

    return (totalApostadoEnCarrera * (1.0 - Carrera.COMISION)) / totalApostadoAlCaballo;
}

    public void actualizarDividendoActual() {
        this.dividendoActual = calcularDividendo();
    }

    public void fijarDividendoFinal() {
        this.dividendoFinal = calcularDividendo();
    }

    public List<Apuesta> getApuestas() {
        return apuestas;
    }

    public void agregarApuesta(Apuesta apuesta) {
        apuestas.add(apuesta);
    }

    public static boolean tieneDividendoValido(Carrera carrera) {
        if (carrera == null || carrera.getRegistros() == null) {
            return false; // No hay registros de participación
        }
        for (Participante registro : carrera.getRegistros()) {
            if (registro != null && registro.calcularDividendo() <= 1) {
                return false; // Hay al menos un caballo con dividendo inválido
            }
        }
        return true; // Todos los caballos tienen dividendo válido
    }

}

/***
 * // Dividendo = (total apostado en la carrera - comisión) / total apostado al
 * // caballo
 * public double obtenerValor() {
 * double totalApostadoAlCaballo = participante.getApuestas().stream()
 * .filter(apuesta -> apuesta.getCaballo().equals(participante))
 * .mapToDouble(Apuesta::getMonto)// suma el monto de cada apuesta al caballo
 * .sum();
 * 
 * return valor;
 * }
 * // Dividendo es válido únicamente cuando:
 * // - cantidad de apuestas al caballo > 0.
 * // - valor es mayor a 1.
 * public boolean esValido() {
 * if (participante.getApuestas() == null ||
 * participante.getApuestas().isEmpty()) {
 * return false; // No hay apuestas al caballo
 * }
 * return valor > 1;
 * }
 * 
 * 
 */
