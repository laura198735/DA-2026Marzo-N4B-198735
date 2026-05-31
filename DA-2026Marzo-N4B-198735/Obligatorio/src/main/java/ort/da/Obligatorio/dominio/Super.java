package ort.da.Obligatorio.dominio;

import lombok.Data;
import ort.da.Obligatorio.excepciones.ObligatorioException;

public class Super extends Modalidad {
    // Se descuenta del saldo lo apostado multiplicado por 2 y en caso de ganar
    // pagará monto
    // apostado * el dividendo del caballo * 3 si dividendo del caballo es mayor o
    // igual a 2 y monto apostado * el
    // dividendo del caballo * 4 si es menor.

    double montoApostado;
    Jugador jugador;

    private Super() {
    };

    public Super(double montoApostado, Jugador jugador) {
        this.montoApostado = montoApostado;
        this.jugador = jugador;
    }

    public double calcularCosto(double monto, Jugador jugador) throws ObligatorioException {
        this.montoApostado = monto;
        // jugador asociado a la apuesta
        if (jugador.getSaldo() < montoApostado) {
            throw new ObligatorioException("Saldo insuficiente para realizar la apuesta.");
        }
        double nuevoSaldo = jugador.getSaldo() - montoApostado * 2; // Restar el monto apostado*2
        // del saldo del jugador en modalidad Simple
        jugador.setSaldo(nuevoSaldo); // Actualizar el saldo del jugador
        return montoApostado; // El costo de la apuesta es el monto apostado
    }

    @Override
    public double calcularPago(Apuesta apuesta) throws ObligatorioException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularPago'");
    }

    @Override
    public double calcularCosto(double monto) throws ObligatorioException {
        this.montoApostado = monto;
        Jugador jugador = this.jugador;
        // jugador asociado a la apuesta
        if (jugador.getSaldo() < montoApostado) {
            throw new ObligatorioException("Saldo insuficiente para realizar la apuesta.");
        }
        double nuevoSaldo = jugador.getSaldo() - montoApostado * 2; // Restar el monto apostado*2
        // del saldo del jugador en modalidad Simple
        jugador.setSaldo(nuevoSaldo); // Actualizar el saldo del jugador
        return montoApostado; // El costo de la apuesta es el monto apostado
    }
}
