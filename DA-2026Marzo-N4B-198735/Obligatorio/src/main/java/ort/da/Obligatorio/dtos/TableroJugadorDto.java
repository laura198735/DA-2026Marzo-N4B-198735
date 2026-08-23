package ort.da.Obligatorio.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
public class TableroJugadorDto {

    private final JugadorResumenDto jugador;
    private final List<ModalidadDisponibleDto> tiposApuesta;
    private final List<CarreraDisponibleDto> carrerasDisponibles;
    private final List<ApuestaRealizadaDto> apuestasRealizadas;

    public record JugadorResumenDto(
            String nombreCompleto,
            String iniciales,
            double saldoActual,
            double totalApostado,
            double totalGanado) {
    }

    public record ModalidadDisponibleDto(String nombre) {
    }

    public record CaballoDisponibleDto(
            int numero,
            String nombre,
            Double dividendoActual) {
    }

    public record CarreraDisponibleDto(
            int numero,
            String nombre,
            Date fecha,
            List<CaballoDisponibleDto> caballos) {
    }

    public record ApuestaRealizadaDto(
            Date fecha,
            int numeroCarrera,
            String nombreCarrera,
            int numeroCaballo,
            String nombreCaballo,
            double montoApostado,
            String tipoApuesta,
            Double montoCobrado,
            Double dividendoFinal,
            String estado) {
    }

    public TableroJugadorDto(JugadorResumenDto jugador, List<ModalidadDisponibleDto> tiposApuesta, List<CarreraDisponibleDto> carrerasDisponibles, List<ApuestaRealizadaDto> apuestasRealizadas) {
        this.jugador = jugador;
        this.tiposApuesta = tiposApuesta;
        this.carrerasDisponibles = carrerasDisponibles;
        this.apuestasRealizadas = apuestasRealizadas;
    }


}
