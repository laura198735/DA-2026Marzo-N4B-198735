package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Simple extends Modalidad {
   
    // El sistema descuenta de el saldo el monto apostado y en caso de ganar acreditará
    //  el monto apostado multiplicado por el dividendo
    // final del caballo.

    double montoApostado;

    @Override
    public double calcularCosto(double monto) throws HipodromoException {
        this.montoApostado = monto;
        //Jugador jugador = this.jugador; 
        //  jugador asociado a la apuesta
        // if (jugador.getSaldo() < montoApostado) {  
        //     throw new ApuestaException("Saldo insuficiente para realizar la apuesta.");
        // }
        //     double nuevoSaldo = jugador.getSaldo() - montoApostado; // Restar el monto apostado 
        //     // del saldo del jugador en modalidad Simple
        //     jugador.setSaldo(nuevoSaldo); // Actualizar el saldo del jugador 
        return montoApostado; // El costo de la apuesta es el monto apostado
    }

    @Override
    public double calcularPago(Apuesta apuesta) {
        // El pago se calcula multiplicando el monto apostado por el dividendo final del caballo
        double dividendoFinal = 0; // Obtener el dividendo final del caballo al que se le apostó
        return montoApostado * dividendoFinal; // El pago es el monto apostado multiplicado por el dividendo final
    }




}
