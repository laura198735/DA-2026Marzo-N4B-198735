package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Modalidad;
import ort.da.Obligatorio.dominio.Participante;

import ort.da.Obligatorio.dominio.Jugador;

import java.util.List;

import lombok.Data;

@Data
public class ApuestaDto {
    private double monto;
    private Modalidad modalidad;
    private Participante participante;
    private Jugador jugador;

    public ApuestaDto() {
    }

    public ApuestaDto(Apuesta apuesta) {
        this.monto = apuesta.getMonto();
        this.modalidad = apuesta.getModalidad();
        this.participante = apuesta.getParticipante();
        this.jugador = apuesta.getJugador();
    }

    public Apuesta toApuesta() {
        Apuesta apuesta = new Apuesta(this.monto, this.modalidad, this.participante);
        return apuesta;
    }

    // convierte una lista de Apuesta a una lista de ApuestaDto
    public static List<ApuestaDto> fromList(List<Apuesta> apuestas) {
        return apuestas.stream()
                .map(ApuestaDto::new)
                .toList();
    }
}
