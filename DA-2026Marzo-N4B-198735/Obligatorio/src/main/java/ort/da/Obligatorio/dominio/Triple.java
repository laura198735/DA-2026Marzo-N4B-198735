package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public class Triple extends Modalidad{
    //     Se descuenta del saldo lo apostado multiplicado por 1.5 y en caso de ganar pagará monto
    //  apostado * el dividendo del caballo * 2 si monto total apostado por todos los jugadores para ese caballo en
    // esa carrera es menor a 100.000, y monto apostado * el dividendo del caballo * 3 si es mayor o igual a 100.000
    //
    double montoApostado;
    Jugador jugador;

    @Override
    public double calcularCosto(double monto) throws HipodromoException {
       this.montoApostado = monto;
        Jugador jugador = this.jugador; 
        //  jugador asociado a la apuesta
        if (jugador.getSaldo() < montoApostado) {  
            throw new HipodromoException("Saldo insuficiente para realizar la apuesta.");
        }
            double nuevoSaldo = jugador.getSaldo() - montoApostado*1.5; // Restar el monto apostado*1.5
            // del saldo del jugador en modalidad Simple
            jugador.setSaldo(nuevoSaldo); // Actualizar el saldo del jugador 
        return montoApostado; // El costo de la apuesta es el monto apostado
    }

    @Override
    public double calcularPago(Apuesta apuesta) throws HipodromoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularPago'");
    }
}
