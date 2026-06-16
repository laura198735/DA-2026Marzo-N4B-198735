package ort.da.Obligatorio.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
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
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    public Carrera(int numeroCarrera, String nombre, Jornada jornada) {
        this.nombre = nombre;
        this.numeroCarrera = numeroCarrera;
        this.jornada = jornada;
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    public void abrirCarrera() throws HipodromoException {
        estadoCarrera.abrirCarrera(this);
    };

    public void cerrarCarrera() throws HipodromoException {
        estadoCarrera.cerrarCarrera(this);
    };

    public boolean estaFinalizada() {
        return estadoCarrera.estaFinalizada();
    }

    public void finalizarCarrera(Caballo caballoGanador) throws HipodromoException {
        estadoCarrera.finalizarCarrera(this, caballoGanador);
    }

    // **ver si se puede apostar */
    public boolean puedeApostar() {
        return estadoCarrera.puedeApostar(this);
    };

    public void agregarApuesta(Caballo caballo, Apuesta apuesta) throws HipodromoException {
        Participante participante = obtenerParticipanteEnCarrera(caballo.getNumero());

        if (participante == null) {
            throw new HipodromoException("El caballo no participa en la carrera. ");
        }
        participante.agregarApuesta(apuesta);
        actualizarDividendosActuales(); //actualiza los dividendos actuales de los participantes con cada nuva apuesta.

    }

    // calcular apostado
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

    // pagado en carrera: se calcula a través de los participantes y sus apuestas
    // ganadoras: monto * dividendoFinal del caballo ganador.
    public double getTotalPagado() {
        double total = 0.0;
        if (registros == null) {
            return total;
        } // a través de los participantes llego al valorDividendo.
        for (Participante participante : registros) {
            if (participante.getApuestas() != null) {
                for (Apuesta apuesta : participante.getApuestas()) {
                    if (apuesta.esApuestaGanadora()) {
                        // Se paga el monto apostado multiplicado por el valor
                        // del dividendo
                        double valorDividendo = participante.getDividendoFinal();
                        total += apuesta.getMonto() * valorDividendo;
                    }
                }
            }

        }
        return total;
    }

    // carrera tiene Lista de participantes => cada participante conoce sus apuestas
    // y actualiza su dividendo.
    public void actualizarDividendosActuales() {
        if (registros == null) {
            return;
        }
        for (Participante participante : registros) {
            if (participante != null) {
                participante.actualizarDividendoActual();
            }
        }
      
    }

    // obtener el dividendo final del caballo ganador para mostrar en el tablero
    public double getDividendoFinalGanador() {
        if (registros == null || caballoGanador == null) {
            return 0.0;
        }

        Participante participanteGanador = obtenerParticipanteEnCarrera(caballoGanador.getNumero());

        if (participanteGanador != null && participanteGanador.getDividendoFinal() > 0) {
            return participanteGanador.getDividendoFinal();
        }

        return 0.0;
    }
    
    public Participante obtenerParticipanteEnCarrera(int numeroCaballo) {
        if (registros == null) {
            return null;
        }
        for (Participante participante : registros) {
            if(participante !=null && 
                participante.getCaballo() != null && 
                participante.getCaballo().getNumero() == numeroCaballo) {
        
                return participante;
            }
        }
        return null;
    }

    // para dtos
    public String obtenerNombreEstadoCarrera() {// para mostrar el estadode la carrera en el tablero.
        return estadoCarrera.getNombreEstado();
    }

    // caballos participantes de carrera para tablero Administrador
    public int obtenerCantidadCaballos() {
        return registros == null ? 0 : registros.size();
    }

    public String obtenerCaballoGanador() {
        return caballoGanador != null ? caballoGanador.getNombre() : "-";
    }

    // cantidad de apuestas realizadas en la carrera
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

    public boolean todosLosDividendosSonValidos() {
        return registros != null && !registros.isEmpty()
                && registros.stream().allMatch(Participante::tieneDividendoValido);
    }

    // si cambia dividendo es inválido hay cambios de estado.
    public void actualizarEstadoPorDividendos() throws HipodromoException {
        estadoCarrera.actualizarEstadoPorDividendo(this);
    }

    public void invalidarDividendosParticipantes() {
        if (registros != null) {
            for (Participante participante : registros) {
                participante.invalidarDividendo();
            }
        }
    }

}
