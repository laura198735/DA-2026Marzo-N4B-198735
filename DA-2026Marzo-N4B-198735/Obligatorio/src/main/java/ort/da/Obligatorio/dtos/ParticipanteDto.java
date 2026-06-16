package ort.da.Obligatorio.dtos;

import java.util.List;

import lombok.Data;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Participante;

@Data
public class ParticipanteDto {

    private int numero;
    private String nombre;
    private double dividendoActual;
    private double dividendoFinal;
    private double totalApostado;
    private int cantidadApuestas;

    public ParticipanteDto(Participante participante) {
        Caballo caballo = participante.getCaballo();
        this.numero = caballo.getNumero();
        this.nombre = caballo.getNombre();
        this.dividendoActual = participante.getDividendoActual();
        this.dividendoFinal = participante.getDividendoFinal();
        this.totalApostado = participante.getTotalApostadoAlCaballo();
        this.cantidadApuestas = participante.getApuestas() == null ? 0 : participante.getApuestas().size();
    }

    public static List<ParticipanteDto> fromCarrera(Carrera carrera) {
        if (carrera == null || carrera.getRegistros() == null) {
            return List.of();
        }

        return carrera.getRegistros().stream()
                .filter(participante -> participante != null && participante.getCaballo() != null)
                .map(ParticipanteDto::new)
                .toList();
    }
}
