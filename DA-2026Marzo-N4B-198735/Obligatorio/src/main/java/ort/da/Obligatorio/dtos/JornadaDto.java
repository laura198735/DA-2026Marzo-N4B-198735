package ort.da.Obligatorio.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;

@Data
public class JornadaDto {
    private int numero;
    private Date fecha;
    private List<CarreraDto> carreras;

    public JornadaDto() {
    }

    public JornadaDto(Jornada jornada) {
        this.numero = jornada.getNumero();
        this.fecha = jornada.getFecha();
        this.carreras = jornada.getCarreras() == null ? null : CarreraDto.fromList(jornada.getCarreras());
    }

    public Jornada toJornada() {
        Jornada jornada = new Jornada();
        jornada.setNumero(this.numero);
        jornada.setFecha(this.fecha);
        jornada.setCarreras(this.carreras == null ? null : this.carreras.stream()
                .map(CarreraDto::toCarrera)
                .toList());
        return jornada;
    }

    public static List<JornadaDto> fromList(List<Jornada> jornadas) {
        return jornadas.stream()
                .map(JornadaDto::new)
                .toList();
    }
}
