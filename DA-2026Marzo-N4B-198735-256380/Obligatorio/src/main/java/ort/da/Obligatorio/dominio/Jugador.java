package ort.da.Obligatorio.dominio;

import java.util.List;

import lombok.Getter;


public class Jugador extends Usuario {
    @Getter
    private double saldo;
    @Getter
    private double totalApostado;
    @Getter
    private List<Apuesta> apuestas;

    public Jugador(String nombreUsuario, String password) {
        super(nombreUsuario, password);
    }

    @Override
    public boolean validar(Credencial credencial) {
        return this.getNombreUsuario().equals(credencial.getNombre()) && this.getPassword().equals(credencial.getPassword());
    }

    public void realizarApuesta(Apuesta apuesta) {
        if (apuesta.getMonto() > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la apuesta.");
        }
        saldo -= apuesta.getMonto();
        totalApostado += apuesta.getMonto();
        apuestas.add(apuesta);
    }

    public void setSaldo(double nuevoSaldo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSaldo'");
    }
    //actualiza saldo después de  que jugador apuesta
    public void descontarSaldo(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto a descontar no puede ser negativo.");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la operación.");
        }
        saldo -= monto;
    }
}
