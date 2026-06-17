package ort.da.Obligatorio.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.observer.Observable;


@Getter
@Setter
public class Carrera extends Observable {

    private int numeroCarrera; // se asigna automáticamente al crear la carrera, utilizando un contador
                               // estático para garantizar la unicidad.
    private static int contadorCarreras = 1; // Contador para generar números de carrera únicos empieza en 1.
    private String nombre;
    private IEstadoCarrera estadoCarrera;
    private Jornada jornada;
    public static final double COMISION = 0.10; // se asume comisión del hipódromo (10%){

    // Lista de registros y jornada correspondiente
    private List<Participante> registros;

    private Caballo caballoGanador;

    public Carrera() {
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    public Carrera(String nombre, Jornada jornada) {
        this.numeroCarrera = contadorCarreras++;
        this.nombre = nombre;
        this.jornada = jornada;
        this.registros = new ArrayList<>();
        this.estadoCarrera = new Definida(); // estado inicial de la carrera es "Definida"
    }

    // acciones con carrera.
    public void abrirCarrera() throws HipodromoException {
        estadoCarrera.abrirCarrera(this);
        notificar(Evento.ESTADO_CARRERA_MODIFICADO);
    };

    public void cerrarCarrera() throws HipodromoException {
        estadoCarrera.cerrarCarrera(this);
        notificar(Evento.ESTADO_CARRERA_MODIFICADO);
    }
//** */
    public void finalizarCarrera(Caballo caballoGanador) throws HipodromoException {
        estadoCarrera.finalizarCarrera(this, caballoGanador);
        notificar(Evento.ESTADO_CARRERA_FINALIZADO);
        notificar(Evento.APUESTA_AGREGADA);
        notificar(Evento.CARRERA_DIVIDENDO_ACTUALIZADO);
        notificar(Evento.CARRERA_DIVIDENDO_FINAL_ACTUALIZADO);
    }

    // **ver si se puede apostar */
    public boolean puedeApostar() {
        return estadoCarrera.puedeApostar(this);
    };
//**/ */
    public void agregarApuesta(Caballo caballo, Apuesta apuesta) throws HipodromoException {
        if (caballo == null) {
            throw new HipodromoException("El caballo no puede ser nulo");
        }
        if (apuesta == null) {
            throw new HipodromoException("La apuesta no puede ser nula");
        }
        if (!puedeApostar()) {
            throw new HipodromoException("No se pueden realizar apuestas en esta carrera en estado: "
                    + estadoCarrera.getNombreEstado());
        }

        Participante participante = obtenerParticipanteEnCarrera(caballo.getNumero());

        if (participante == null) {
            throw new HipodromoException("El caballo no participa en la carrera. ");
        }
        participante.agregarApuesta(apuesta);

        actualizarDividendosActuales(); // actualiza los dividendos actuales de los participantes con cada nuva apuesta.
        notificar(Evento.APUESTA_AGREGADA);
        notificar(Evento.CARRERA_TOTAL_APOSTADO_ACTUALIZADO);
        notificar(Evento.CARRERA_DIVIDENDO_ACTUALIZADO);

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

    /**
     * pagado en carrera: se calcula a través de los participantes y sus apuestas
     * ganadoras: monto * dividendoFinal del caballo ganador.
     */
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

    /*
     * carrera tiene Lista de participantes => cada participante y actualiza su
     * dividendo actual.
     */
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

    public boolean todosLosDividendosSonValidos() {
        return registros != null && !registros.isEmpty()
                && registros.stream().allMatch(Participante::tieneDividendoValido);
    }

    // si 1 dividendo es inválido cambia estado estable a abierta.
    public void actualizarEstadoPorDividendos() throws HipodromoException {
        estadoCarrera.actualizarEstadoPorDividendo(this);
        notificar(Evento.ESTADO_CARRERA_MODIFICADO);
    }

    public void fijarDividendoFinalEnParticipantes() {
        if (registros != null) {
            for (Participante participante : registros) {
                participante.fijarDividendoFinal();
            }
        }
    }

    public void invalidarDividendosParticipantes() {
        if (registros != null) {
            for (Participante participante : registros) {
                participante.invalidarDividendo();
                notificar(Evento.ESTADO_CARRERA_MODIFICADO);
            }
        }
        notificar(Evento.CARRERA_DIVIDENDO_ACTUALIZADO);
        notificar(Evento.ESTADO_CARRERA_MODIFICADO);
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
            if (participante != null &&
                    participante.getCaballo() != null &&
                    participante.getCaballo().getNumero() == numeroCaballo) {

                return participante;
            }
        }
        return null;
    }

    // tablero administrador
    // para dtos
    public String obtenerNombreEstadoCarrera() {// para mostrar el estadode la carrera en el tablero.
        return estadoCarrera.getNombreEstado();
    }

    // caballos participantes de carrera
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

    public boolean estaFinalizada() {
        return estadoCarrera.estaFinalizada();
    }

    public boolean estaCerrada() {
        return estadoCarrera.estaCerrada();
    }

  

}
