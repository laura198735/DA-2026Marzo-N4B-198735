package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Simple  implements IModalidad {

    // El sistema descuenta de el saldo el monto apostado y en caso de ganar
    // acreditará
    // el monto apostado multiplicado por el dividendo
    // final del caballo.

    @Override
    public String getNombre() {
        return "Simple";
    }

    @Override
    public double calcularCosto(double montoApostado) throws HipodromoException {
        return montoApostado; // El costo de la apuesta es el monto apostado
    }

    @Override
    public double calcularPago(double montoApostado, Participante participante) throws HipodromoException {
        return montoApostado * participante.getDividendoFinal();
    }

}
