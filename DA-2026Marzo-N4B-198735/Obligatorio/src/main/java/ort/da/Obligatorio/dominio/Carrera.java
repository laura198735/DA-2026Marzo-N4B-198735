package ort.da.Obligatorio.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ort.da.Obligatorio.excepciones.EstadoException;
import ort.da.Obligatorio.excepciones.HipodromoException;

@Getter
@Setter
public class Carrera {

    private int numeroCarrera;
    private String nombre;
    private IEstadoCarrera estadoCarrera;
    private Jornada jornada;
    public static final double COMISION = 0.15; // se asume comisión del hipódromo (15%){

    // Lista de registros y jornada correspondiente
    private List<Participante> registros;

    private Caballo caballoGanador;

    public Carrera() {

    }

    public Carrera(int numeroCarrera, String nombre, Jornada jornada) {
        this.nombre = nombre;
        this.numeroCarrera = numeroCarrera;
        this.jornada = jornada;
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    public void abrirCarrera() throws EstadoException {
        estadoCarrera.abrirCarrera(this);
    };

    public void cerrarCarrera() throws EstadoException {
        estadoCarrera.cerrarCarrera(this);
    };

    public boolean estaFinalizada() {
        return estadoCarrera instanceof Finalizada;
    }

    public IEstadoCarrera obtenerEstadoCarrera(Carrera carrera) {
        return this.getEstadoCarrera();
    }

    // ganador y finalizo la carrera

    public void finalizarCarrera(Caballo caballoGanador) throws EstadoException, HipodromoException {
        if (caballoGanador == null) {
            throw new HipodromoException("Debe seleccionar un caballo ganador.");
        }

        if (registros == null || registros.isEmpty()) {
            throw new HipodromoException("No hay participantes en la carrera para finalizarla.");
        }

        if (obtenerParticipanteEnCarrera(caballoGanador.getNumero()) == null) {
            throw new HipodromoException("El caballo ganador debe participar en la carrera.");
        }

        if (registros != null) {
            for (Participante participante : registros) {
                if (participante != null) {
                    participante.fijarDividendoFinal();
                }
            }
        }

        this.caballoGanador = caballoGanador;
        this.estadoCarrera = new Finalizada();
    }

    // **ver si se puede apostar */
    public boolean puedeApostar() {
        return estadoCarrera.puedeApostar(this);
    };

    // calculos
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
                    if (apuesta.esApuestaGanadora()) {
                        // Se paga el monto apostado multiplicado por el valor
                        // del dividendo
                        double valorDividendo = registro.getDividendoFinal();
                        total += apuesta.getMonto() * valorDividendo;
                    }
                }
            }

        }
        return total;
    }

    // obtener el dividendo final del caballo ganador para mostrar en el tablero

    public double getDividendoFinalGanador() {
        if (registros == null || caballoGanador == null) {
            return 0.0;
        }

        for (Participante participante : registros) {
            if (participante != null && participante.esCaballoGanador()) {
                return participante.getDividendoFinal();
            }
        }

        return 0.0;
    }

    // obtener carrera por numero y caballo ganador para tablero
    public Carrera obtenerCarreraPorNumero(int numeroCarrera) throws HipodromoException {
        try {
            Carrera carrera = this.obtenerCarreraPorNumero(numeroCarrera);
            for (Participante participante : carrera.getRegistros()) {
                if (participante.getCarrera().getNumeroCarrera() == numeroCarrera) {
                    carrera = participante.getCarrera();
                    break;
                }
            }
            if (carrera == null) {
                throw new HipodromoException("No se encontró la carrera con número: " + numeroCarrera);
            }
            return carrera;

        } catch (Exception e) {
            throw new HipodromoException("Error al obtener la carrera por número: " + e.getMessage());
        }
    }

    public Participante obtenerParticipanteEnCarrera(int numero) {
        for (Participante participante : registros) {

            if (participante.getCaballo().getNumero() == numero) {
                return participante;
            }
        }
        return null;
    }

    // para dtos
    public String obtenerNombreEstadoCarrera() {//para mostrar el estadode la carrera en el tablero.
        return estadoCarrera.getClass().getSimpleName();
    }

    // caballos participantes de carrera para tablero Administrador
    public int obtenerCantidadCaballos() {
        return registros == null ? 0 : registros.size();
    }

    public String obtenerCaballoGanador() {
        return caballoGanador != null ? caballoGanador.getNombre() : "-";
    }

    // cantidad de apuestas realizadas en la carrera para tablero Administrador
    public int obtenerCantidadApuestas() {
        int cantidad = 0;
        if (registros == null) {
            return cantidad;
        }
        for (Participante registro : registros) {
            if (registro.getApuestas() != null) {
                cantidad += registro.getApuestas().size();
            }
        }
        return cantidad;
    }

    public void invalidarDividendosParticipantes() {
        if (registros != null) {
            for (Participante participante : registros) {
                participante.invalidarDividendo();
            }
        }
    }

  

}
