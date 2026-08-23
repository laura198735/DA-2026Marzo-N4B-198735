package ort.da.Obligatorio.dominio;

import lombok.Getter;

import ort.da.Obligatorio.excepciones.HipodromoException;


@Getter
public class Apuesta {
    private final int id; // ID único de la apuesta
    private static int contadorId = 1; // Contador para generar IDs únicos
    private double monto;
    private Jugador jugador;
    private IModalidad modalidad;
    private Participante participante; // El caballo al que se le apuesta

    public Apuesta(double monto, IModalidad modalidad, Participante participante) {
        this.id = contadorId++;
        this.monto = monto;
        this.modalidad = modalidad;
        this.participante = participante;
        this.jugador = null; // El jugador se asigna cuando se realiza la apuesta a través del método realizarApuesta del jugador
    }

    public Apuesta(Jugador jugador2, double monto2, String modalidadNombre, int numeroCarrera, int numeroCaballo) throws HipodromoException {
        this.id = contadorId++;
        this.monto = monto2;
        this.jugador = jugador2;
        this.modalidad = ModalidadFactory.getModalidad(modalidadNombre);
        Carrera carrera = participante.getCarrera();
        if (carrera == null) {
            throw new HipodromoException("No se encontró la carrera con el número proporcionado.");
        }
        Participante participanteEncontrado = null;
        for (Participante participante : carrera.getRegistros()) {
            if (participante.getCaballo().getNumero() == numeroCaballo) {
                participanteEncontrado = participante;
                break;
            }
        }
        if (participanteEncontrado == null) {
            throw new HipodromoException("No se encontró el caballo con el número proporcionado en la carrera.");
        }
        this.participante = participanteEncontrado;
    }

    public void asignarJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public double calcularCosto() throws HipodromoException {
        return modalidad.calcularCosto(monto);
    }

    public double calcularPago() throws HipodromoException {
        return modalidad.calcularPago(monto, participante);
    }

    public boolean esApuestaGanadora() {
        return participante != null && participante.esCaballoGanador();

    }

    // para el hipodromo
    public double calcularGanancia() throws HipodromoException {
        if (esApuestaGanadora()) {
            double valorDividendo = participante.getDividendoFinal();
            return monto * valorDividendo;
        }
        return 0.0; // Si la apuesta no es ganadora, no se gana nada
    }
 
    public String getModalidadNombre() {
        return modalidad.getNombre();
    }

    public IModalidad getModalidadesDisponibles() {
        return modalidad;
    }
}
