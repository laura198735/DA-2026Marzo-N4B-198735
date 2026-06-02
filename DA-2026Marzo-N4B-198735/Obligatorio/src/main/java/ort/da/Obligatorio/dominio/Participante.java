package ort.da.Obligatorio.dominio;

import java.util.List;
import lombok.Getter;

@Getter
public class Participante {
    private Caballo caballo;
    private Carrera carrera;
    private Estado estadoCarrera;
    private double dividendoActual;
    private double dividendoFinal;
    private List<Apuesta> apuestas;

    public Participante(Caballo caballo, Carrera carrera, double dividendo) {
        this.caballo = caballo;
        this.carrera = carrera;
        this.dividendoActual = dividendo;

    }
    //Verifico si el caballo es el que participa por el numero de caballo para saber si corrió
    public boolean esCaballoDelRegistro(Participante registro, Caballo caballo) {
        return registro != null
                && registro.getCaballo() != null
                && caballo != null
                && registro.getCaballo().getNumero() == caballo.getNumero();
    }

    public boolean esCaballoGanador(Carrera carrera) {
        return carrera != null
                && carrera.getCaballoGanador() != null
               && caballo.getNumero() == carrera.getCaballoGanador().getNumero();
    }
    /***se tiene que calcular cada vez que se hace una apuesta*/
    public double calcularDividendo(double comision) {
        if (carrera == null)
            return 0.0;
        double totalApostadoEnCarrera = carrera.getTotalApostado();
        double totalApostadoAlCaballo = 0.0;//ver
        if (apuestas != null) {
            for (Apuesta a : apuestas) {
                totalApostadoAlCaballo += a.getMonto();
            }
        }
        if (totalApostadoAlCaballo <= 0)
            return 0.0;
        double valor = (totalApostadoEnCarrera * (1.0 - comision)) / totalApostadoAlCaballo;
        return valor;
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

}