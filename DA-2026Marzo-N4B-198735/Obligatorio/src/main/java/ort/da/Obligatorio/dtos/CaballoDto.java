package ort.da.Obligatorio.dtos;

import java.util.List;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Participante;

import lombok.Data;

@Data
public class CaballoDto {

    private int numero;
    private String nombre;
    private double dividendoActual;
    private double dividendoFinal;
    private double totalApostado;
    private int cantidadApuestas;

    public CaballoDto(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public CaballoDto(Caballo caballo) {
        this.numero = caballo.getNumero();
        this.nombre = caballo.getNombre();

    }

    public CaballoDto(Participante participante) {
        Caballo caballo = participante.getCaballo();
        this.numero = caballo.getNumero();
        this.nombre = caballo.getNombre();
        this.dividendoActual = participante.calcularDividendo();
        this.dividendoFinal = participante.getDividendoFinal();
        this.totalApostado = participante.getTotalApostadoAlCaballo();
        this.cantidadApuestas = participante.getApuestas() == null ? 0 : participante.getApuestas().size();
    }

    public static CaballoDto from(int numero, String nombre) {
        return new CaballoDto(numero, nombre);
    }

    public static List<CaballoDto> fromList(List<Caballo> caballos) {
        return caballos.stream()
                .map(c -> new CaballoDto(c.getNumero(), c.getNombre()))
                .toList();
    }

    public static List<CaballoDto> fromCarrera(Carrera carrera) {
        if (carrera == null || carrera.getRegistros() == null) {
            return List.of();
        }

        return carrera.getRegistros().stream()
                .filter(participante -> participante != null && participante.getCaballo() != null)
                .map(CaballoDto::new)
                .toList();
    }

}
