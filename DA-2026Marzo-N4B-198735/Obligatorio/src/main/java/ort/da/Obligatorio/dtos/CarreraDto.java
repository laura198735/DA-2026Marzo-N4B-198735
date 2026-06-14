
package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Carrera;

import java.util.List;

import lombok.Getter;

@Getter
public class CarreraDto {

    private int numero;
    private String nombre;
    private String estado;
    private int cantidadCaballos;
    private double totalApostado;
    private int cantidadApuestas;

    public CarreraDto() {
    }

    public CarreraDto(Carrera carrera) {
        this.numero = carrera.getNumeroCarrera();
        this.nombre = carrera.getNombre();
        this.estado = carrera.obtenerNombreEstadoCarrera();
        this.cantidadCaballos = carrera.obtenerCantidadCaballos();
        this.totalApostado = carrera.getTotalApostado();
        this.cantidadApuestas = carrera.obtenerCantidadApuestas();
    }

    // Convertir una carrera a un dto
    public static CarreraDto from(Carrera carrera) {
        return new CarreraDto(carrera);
    }

    // Convertir un dto a una carrera
    public Carrera toCarrera() {
        Carrera carrera = new Carrera();
        carrera.setNumeroCarrera(this.numero);
        carrera.setNombre(this.nombre);

        return carrera;
    }

    // convierte una lista de carreras a una lista de dto
    public static List<CarreraDto> fromList(List<Carrera> carreras) {
        return carreras == null ? List.of()
                : carreras.stream()
                        .filter(carrera -> carrera != null)
                        .map(CarreraDto::from)
                        .toList();
    }

    // para usar en tableros
    public static record CarreraResultadoDto(
            int numero,
            String nombre,
            int cantidadCaballos,
            double totalApostado,
            double totalPagado,
            String caballoGanador,
            double dividendoFinal) {

        public CarreraResultadoDto(Carrera carrera) {
            this(
                    carrera.getNumeroCarrera(),
                    carrera.getNombre(),
                    carrera.obtenerCantidadCaballos(),
                    carrera.getTotalApostado(),
                    carrera.getTotalPagado(),
                    carrera.getCaballoGanador() == null ? "" : carrera.getCaballoGanador().getNombre(),
                    carrera.getDividendoFinalGanador());
        }
    }

    // para usar en tableros
    public static record CarreraProximaDto(
            int numero,
            String nombre,
            String estado,
            int cantidadCaballos,
            double totalApostado,
            int cantidadApuestas) {

        public CarreraProximaDto(Carrera carrera) {
            this(
                    carrera.getNumeroCarrera(),
                    carrera.getNombre(),
                    carrera.obtenerNombreEstadoCarrera(),
                    carrera.obtenerCantidadCaballos(),
                    carrera.getTotalApostado(),
                    carrera.obtenerCantidadApuestas());
        }
    }
}