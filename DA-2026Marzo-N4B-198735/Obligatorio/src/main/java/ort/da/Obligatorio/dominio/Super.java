package ort.da.Obligatorio.dominio;

import lombok.Data;
import ort.da.Obligatorio.excepciones.HipodromoException;

public class Super extends Modalidad {
    // Se descuenta del saldo lo apostado multiplicado por 2 y en caso de ganar
    // pagará monto
    // apostado * el dividendo del caballo * 3 si dividendo del caballo es mayor o
    // igual a 2 y monto apostado * el
    // dividendo del caballo * 4 si es menor.

    @Override
    public String getNombre() {
        return "Super";
    }

    @Override
    public double calcularCosto(double montoApostado) throws HipodromoException {
        return montoApostado * 2; // El costo de la apuesta es el monto apostado multiplicado por 2

    }

    @Override
    public double calcularPago(double montoApostado, Participante participante) throws HipodromoException {
        ;
        if (participante.getDividendoFinal() >= 2) {
            return montoApostado * participante.getDividendoFinal() * 3;
        } else {
            return montoApostado * participante.getDividendoFinal() * 4;
        }
    }

}
