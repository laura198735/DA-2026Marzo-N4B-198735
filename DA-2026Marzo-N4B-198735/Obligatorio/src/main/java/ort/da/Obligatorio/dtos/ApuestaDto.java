package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Apuesta;


import java.util.List;

import lombok.Data;

@Data
public class ApuestaDto {
    private double monto;
    private String modalidad;
    private String participante;
    private String jugador;

    public ApuestaDto() {
    }

    public ApuestaDto(Apuesta apuesta) {
        this.monto = apuesta.getMonto();
        this.modalidad = apuesta.getModalidad().getNombre();
        this.participante = apuesta.getParticipante().getNombreCaballo();
        this.jugador = apuesta.getJugador().getNombreUsuario();
    }

 

    // convierte una lista de Apuesta a una lista de ApuestaDto
    public static List<ApuestaDto> fromList(List<Apuesta> apuestas) {
        return apuestas.stream()
                .map(ApuestaDto::new)
                .toList();
    }
}
