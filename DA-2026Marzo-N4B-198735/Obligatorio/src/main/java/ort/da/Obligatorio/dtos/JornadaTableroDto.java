package ort.da.Obligatorio.dtos;

import java.util.Date;

import lombok.Data;
@Data
public class JornadaTableroDto {

    private final int numero;
    private final Date dia;

    private JornadaTableroDto(int numero, Date dia) {
        this.numero = numero;
        this.dia = dia;
    }

    public static JornadaTableroDto from(int numero, Date dia) {
        return new JornadaTableroDto(numero, dia);
    }

    



}
