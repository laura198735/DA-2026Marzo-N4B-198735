package ort.da.Obligatorio.servicios;

import java.util.List;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Usuario;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.excepciones.AutenticacionException;

public class ServicioAutenticacion {
    private List<Administrador> administradores;
    private List<Jugador> jugadores;

    public ServicioAutenticacion() {
        this.administradores = new java.util.ArrayList<>();
        this.jugadores = new java.util.ArrayList<>();
        // Datos de prueba por defecto (coinciden con los mensajes en ObligatorioApplication)
        try {
            Administrador administrador1 = new Administrador("a1", "a1");
            Administrador administrador2 = new Administrador("a2", "a2");
            
            Jugador jugador1 = new Jugador("j1", "j1");
            Jugador jugador2 = new Jugador("j2", "j2");
            jugador1.setNombre("Carlos Méndez");
            jugador2.setNombre("Ana Rodríguez");
            jugador1.setSaldo(14850);
            jugador2.setSaldo(12000);
            this.administradores.add(administrador1);
            this.administradores.add(administrador2);
            this.jugadores.add(jugador1);
            this.jugadores.add(jugador2);
        } catch (Exception e) {
            // No debe ocurrir: si hay error, dejamos las listas vacías
        }
    }

    public ServicioAutenticacion(List<Administrador> administradores) {
        // Inicializar con la lista proporcionada (hacer copia defensiva)
        this.administradores = administradores == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(administradores);
        this.jugadores = new java.util.ArrayList<>();
    }

    public Usuario autenticar(Credencial credencial) throws AutenticacionException {
        for (Administrador admin : administradores) {
            if (admin.validar(credencial)) {
                return admin;
            }
        }

        for (Jugador jugador : jugadores) {
            if (jugador.validar(credencial)) {
                return jugador;
            }
        }

        throw new AutenticacionException("Credenciales inválidas");
    }

    public Administrador autenticarAdministrador(Credencial credencial) throws AutenticacionException {
        for (Administrador admin : administradores) {
            if (admin.validar(credencial)) {
                return admin;
            }
        }

        throw new AutenticacionException("Credenciales invalidas para administrador");
    }

    public Jugador autenticarJugador(Credencial credencial) throws AutenticacionException {
        for (Jugador jugador : jugadores) {
            if (jugador.validar(credencial)) {
                return jugador;
            }
        }

        throw new AutenticacionException("Credenciales invalidas para jugador");
    }
}
