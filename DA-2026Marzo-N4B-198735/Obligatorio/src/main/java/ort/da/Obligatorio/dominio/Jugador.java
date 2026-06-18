package ort.da.Obligatorio.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.Obligatorio.excepciones.HipodromoException;


public class Jugador extends Usuario {
    @Getter
    private double saldo;
    @Getter
    private double totalApostado;
    @Getter
    private List<Apuesta> apuestas;

   
    public Jugador() {
        super("", "");
        this.apuestas = new ArrayList<>();
    }
    public Jugador(String nombreUsuario, String password) {
        super(nombreUsuario, password);
        this.apuestas = new java.util.ArrayList<>();
    }

        @Override
    public boolean validar(Credencial credencial) {
        return this.getNombreUsuario().equals(credencial.getNombre()) && this.getPassword().equals(credencial.getPassword());
    }

    public void realizarApuesta(Apuesta apuesta) throws HipodromoException {
        if (apuesta == null) {
            throw new HipodromoException("La apuesta no puede ser nula.");
        }
        double costo = apuesta.calcularCosto();
        if (costo > saldo) {
            throw new HipodromoException("Saldo insuficiente para realizar la apuesta.");
        }

        saldo -= costo;//se resta el costo del saldo
        totalApostado += apuesta.getMonto();
        apuesta.asignarJugador(this);
        apuestas.add(apuesta);
        notificar(Evento.JUGADOR_APUESTA_AGREGADA);
        notificar(Evento.JUGADOR_SALDO_ACTUALIZADO);
    }

    public void setSaldo(double nuevoSaldo) {
        this.saldo = nuevoSaldo;
        notificar(Evento.JUGADOR_SALDO_ACTUALIZADO);
    }

    public double getTotalGanado() {
        double total = 0.0;
        for (Apuesta apuesta : apuestas) {
            if (apuesta == null || !apuesta.esApuestaGanadora()) {
                continue;
            }
            try {
                total += apuesta.calcularGanancia();
            } catch (HipodromoException e) {
                // Si no se puede calcular, no se suma al total ganado.
            }
        }
        return total;
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
        notificar(Evento.JUGADOR_SALDO_ACTUALIZADO);
    }
}
