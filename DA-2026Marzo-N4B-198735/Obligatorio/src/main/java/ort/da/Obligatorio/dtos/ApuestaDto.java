package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Modalidad;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dominio.Carrera;

import java.util.List;

import lombok.Data;

@Data
public class ApuestaDto {
    private double monto;
    private Modalidad modalidad;
    private Participante caballo;
    private Carrera carrera;

    public ApuestaDto() {
    }

    public ApuestaDto(Apuesta apuesta) {
        this.monto = apuesta.getMonto();
        this.modalidad = apuesta.getModalidad();
        this.caballo = apuesta.getCaballo();
        this.carrera = apuesta.getCarrera();
    }

    public Apuesta toApuesta() {
        Apuesta apuesta = new Apuesta(this.monto, this.modalidad, this.caballo);
        return apuesta;
    }

    // convierte una lista de Apuesta a una lista de ApuestaDto
    public static List<ApuestaDto> fromList(List<Apuesta> apuestas) {
        return apuestas.stream()
                .map(ApuestaDto::new)
                .toList();
    }
}
