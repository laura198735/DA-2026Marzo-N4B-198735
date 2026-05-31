package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Estado;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dominio.Jornada;

import java.util.List;

import lombok.Data;

@Data
public class CarreraDto {
    private int numero;
    private String nombre;
    private Estado estado;
    private List<Participante> registros;
    private Jornada jornada;

    public CarreraDto() {
    }

    public CarreraDto(Carrera carrera) {
        this.numero = carrera.getNumero();
        this.nombre = carrera.getNombre();
        this.estado = carrera.getEstado();
        this.registros = carrera.getRegistros();
        this.jornada = carrera.getJornada();
    }

    public Carrera toCarrera() {
        Carrera carrera = new Carrera();
        carrera.setNumero(this.numero);
        carrera.setNombre(this.nombre);
        carrera.setEstado(this.estado);
        carrera.setRegistros(this.registros);
        carrera.setJornada(this.jornada);
        return carrera;
    }

    // convierte una lista de Carrera a una lista de CarreraDto
    public static List<CarreraDto> fromList(List<Carrera> carreras) {
        return carreras.stream()
                .map(CarreraDto::new)
                .toList();
    }
}
