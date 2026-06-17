package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Triple implements IModalidad {
    // Se descuenta del saldo lo apostado multiplicado por 1.5 y en caso de ganar
    // pagará monto
    // apostado * el dividendo del caballo * 2 si monto total apostado por todos los
    // jugadores para ese caballo en
    // esa carrera es menor a 100.000, y monto apostado * el dividendo del caballo *
    // 3 si es mayor o igual a 100.000
    //
    
    @Override
    public String getNombre() {
        return "Triple";
    }

    @Override
    public double calcularCosto(double montoApostado) throws HipodromoException {
        return montoApostado * 1.5; // El costo de la apuesta es el monto apostado multiplicado por 1.5
    }

    @Override
    public double calcularPago(double montoApostado, Participante participante) throws HipodromoException {
        double totalApostadoAlCaballo = participante.getTotalApostadoAlCaballo();
        if (totalApostadoAlCaballo < 100000) {
            return montoApostado * participante.getDividendoFinal() * 3;
        } else {
        return montoApostado * participante.getDividendoFinal() * 2;
        }
    }
}
