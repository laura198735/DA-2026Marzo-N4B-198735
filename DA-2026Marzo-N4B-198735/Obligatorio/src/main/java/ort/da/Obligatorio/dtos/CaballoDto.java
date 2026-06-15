package ort.da.Obligatorio.dtos;

import java.util.List;
import ort.da.Obligatorio.dominio.Caballo;

import lombok.Data;

@Data
public class CaballoDto {

    private int numero;
    private String nombre;

    public CaballoDto(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public CaballoDto(Caballo caballo) {
        this.numero = caballo.getNumero();
        this.nombre = caballo.getNombre();

    }
    public static CaballoDto from(int numero, String nombre) {
        return new CaballoDto(numero, nombre);
    }

    public static List<CaballoDto> fromList(List<Caballo> caballos) {
        return caballos.stream()
                .map(c -> new CaballoDto(c.getNumero(), c.getNombre()))
                .toList();
    }

}
