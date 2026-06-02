package ort.da.Obligatorio.dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ort.da.Obligatorio.excepciones.EstadoException;
import ort.da.Obligatorio.excepciones.ObligatorioException;

@Getter
@Setter
public class Carrera {

    private int numero;
    private String nombre;
    private Estado estado;

    // Lista de registros y jornada correspondiente
    public List<Participante> registros;
    private Jornada jornada;
    private IEstadoCarrera estadoCarrera;
    private Caballo caballoGanador;

    public Carrera() {

    }

    public Carrera(String nombre, Date fecha, double monto, Estado estado) {
        this.nombre = nombre;
        this.estado = estado;
        this.registros = new ArrayList<>();
        this.jornada = new Jornada();

    }

    public void abrirCarrera() {
        this.setEstadoCarrera(new Definida());
        ; // estado inicial de la carrera es"Definida"
    }

    public void cerrarCarrera() throws EstadoException {
        estadoCarrera.cerrarCarrera(this);
    };
//ver si el ganador se asigna en carrera cerrada.
    public int asignarGanador() throws EstadoException {
        if(estadoCarrera instanceof Cerrada){//** */
            return estadoCarrera.asignarGanador(this);
        } else {
            throw new EstadoException("No se puede asignar un ganador a una carrera que no está cerrada");
        }
    };;

    public boolean puedeApostar() {
        return estadoCarrera.puedeApostar(this);
    };

    // **************TODO ver
    public boolean esHabilitada(Caballo caballo) throws ObligatorioException {
        if (registros == null || registros.isEmpty()) {
            throw new ObligatorioException("La carrera no está habilitada para apostar.");
        }
        for (Participante registro : registros) {
            if (esCaballoDelRegistro(registro, caballo)) {
                return true; // El caballo está registrado en la carrera, por lo tanto está habilitada para
                             // apostar
            }
        }

        throw new ObligatorioException("La carrera no está habilitada para apostar.");
    }

    public boolean esCaballoDelRegistro(Participante registro, Caballo caballo) {
        Caballo caballoRegistro = registro.getCaballo();
        return caballo.equals(caballoRegistro);
    }

    public boolean esGanador(){
        return caballoGanador != null;
    }

    public double getTotalApostado() {
        double total = 0.0;
        for (Participante registro : registros) {
            for (Apuesta apuesta : registro.getApuestas()) {
                total += apuesta.getMonto();
            }
        }
        return total;
    }

    public double getTotalPagado() {
        double total = 0.0;
        for (Participante registro : registros) {
            for (Apuesta apuesta : registro.getApuestas()) {
                if (apuesta.isGanadora()) {
                    double valorDividendo = registro.getDividendoActual();
                    total += apuesta.getMonto() * valorDividendo; // Se paga el monto apostado multiplicado por el valor
                                                                  // del dividendo
                }
            }
        }
        return total;
    }

}