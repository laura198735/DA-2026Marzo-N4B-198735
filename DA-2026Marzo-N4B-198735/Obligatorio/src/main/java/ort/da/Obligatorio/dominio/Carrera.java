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

    private int numeroCarrera;
    private String nombre;
    private IEstadoCarrera estadoCarrera;
    private Jornada jornada;

    // Lista de registros y jornada correspondiente
    private List<Participante> registros;

    private Caballo caballoGanador;

    public Carrera() {

    }

    public Carrera(int numeroCarrera, String nombre, Jornada jornada) {
        this.nombre = nombre;
        this.numeroCarrera = numeroCarrera;
        this.jornada = new Jornada();
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    public void abrirCarrera() throws EstadoException {
        estadoCarrera.abrirCarrera(this);
    };

    public void cerrarCarrera() throws EstadoException {
        estadoCarrera.cerrarCarrera(this);
    };

    // ganador.
    public int asignarGanador() throws EstadoException {
        return estadoCarrera.asignarGanador(this);
    };

    // **ver si se puede apostar */
    public boolean puedeApostar() {
        return estadoCarrera.puedeApostar(this);
    };

    // calcular apostado y pagado en carrera
    public double getTotalApostado() {
        double total = 0.0;
        for (Participante registro : registros) {
            if (registro.getApuestas() != null) {
                for (Apuesta apuesta : registro.getApuestas()) {
                    total += apuesta.getMonto();
                }
            }
        }
        return total;
    }

    public double getTotalPagado() {
        double total = 0.0;
        if (registros == null) {
            return total;
        } // a través de los participantes llego al valorDividendo.
        for (Participante registro : registros) {
            if (registro.getApuestas() != null) {
                for (Apuesta apuesta : registro.getApuestas()) {
                    if (apuesta.isGanadora()) {
                        // Se paga el monto apostado multiplicado por el // valor
                        // del dividendo
                        double valorDividendo = registro.getDividendoActual();
                        total += apuesta.getMonto() * valorDividendo;
                    }
                }
            }

        }
        return total;
    }
}
