package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.IEstadoCarrera;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dtos.JornadaDto.CarreraTableroDto;
import ort.da.Obligatorio.dominio.Jornada;

import java.util.List;

import lombok.Data;

@Data
public class CarreraDto {
    private int numero;
    private String nombre;
    private IEstadoCarrera  estadoCarrera;
    private List<Participante> registros;
    private Jornada jornada;

    public CarreraDto() {
    }

    public CarreraDto(Carrera carrera) {
        this.numero = carrera.getNumeroCarrera();
        this.nombre = carrera.getNombre();
        this.estadoCarrera = carrera.getEstadoCarrera();
        this.registros = carrera.getRegistros();
        this.jornada = carrera.getJornada();
    }

    public Carrera toCarrera() {
        Carrera carrera = new Carrera();
        carrera.setNumeroCarrera(this.numero);
        carrera.setNombre(this.nombre);
        carrera.setEstadoCarrera(this.estadoCarrera);
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

        private CarreraTableroDto crearCarreraTablero(Carrera carrera) {
        return new CarreraTableroDto(
                carrera.getNumeroCarrera(),
                carrera.getNombre(),
                carrera.obtenerEstadoCarrera(carrera),
                carrera.obtenerCantidadCaballos(carrera),
                carrera.getTotalApostado(),
                carrera.getTotalPagado(),
                carrera.getCaballoGanador() != null ? carrera.getCaballoGanador().getNombre() : "-",
                "-",
                carrera.obtenerCantidadApuestas(carrera));
    }
}
