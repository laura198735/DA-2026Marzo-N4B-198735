package ort.da.Obligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.excepciones.HipodromoException;

@Getter
public class ServicioCarrera {

    private List<Carrera> carreras;
    private ServicioApuesta servicioApuesta;
    private ServicioJornada servicioJornada;

    public ServicioCarrera(ServicioJornada servicioJornada, ServicioApuesta servicioApuesta) {
        this.servicioJornada = servicioJornada;
        this.servicioApuesta = servicioApuesta;
        this.carreras = new ArrayList<>();
    }

    public void abrirCarrera(Carrera carrera) throws HipodromoException {
        try {
            carrera.getEstadoCarrera().abrirCarrera(carrera);

        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    public void cerrarCarrera(int numeroCarrera) throws HipodromoException {
        Carrera carrera = buscarCarreraPorNumero(numeroCarrera);

        try {
            carrera.cerrarCarrera();

        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    public void finalizarCarreraConGanador(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        try {
            carrera.finalizarCarrera(caballoGanador);
        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    public void finalizarCarreraYPagar(Carrera carrera, Caballo caballoGanador) throws HipodromoException {
        try {
            carrera.finalizarCarrera(caballoGanador);
            servicioApuesta.pagarApuestasGanadoras(carrera);
        } catch (Exception e) {
            throw new HipodromoException(e.getMessage());
        }
    }

    // CU Gestionar carreras
    public Carrera buscarCarreraPorNumero(int numeroCarrera) throws HipodromoException {
        if (servicioJornada == null || servicioJornada.getJornadas() == null) {
            throw new HipodromoException("No hay jornadas cargadas para buscar la carrera.");
        }
        for (Jornada jornada : servicioJornada.getJornadas()) {
            if (jornada != null && jornada.getCarreras() != null) {// las carreras se cargan en Jornadas en la precarga
                                                                   // de datos.

                for (Carrera carrera : jornada.getCarreras()) {
                    if (carrera != null && carrera.getNumeroCarrera() == numeroCarrera) {
                        return carrera;
                    }
                }
            }
        }
        throw new HipodromoException("No se encontró la carrera con número: " + numeroCarrera);
    }

    public boolean caballoParticipaEnCarrera(Carrera carrera, int numeroCaballo) {
        if (carrera == null || carrera.getRegistros() == null) {
            return false;
        }

        return carrera.getRegistros().stream()
                .map(Participante::getCaballo)
                .filter(caballo -> caballo != null && caballo.getNumero() == numeroCaballo)
                .findFirst()
                .orElse(null) != null;

    }

    // CU Gestionar carreras - mostrar carrera seleccionada y caballos participantes
    public Caballo buscarCaballoPorNumero(int numeroCaballo) throws HipodromoException {
        if (servicioJornada == null || servicioJornada.getJornadas() == null) {
            throw new HipodromoException("No hay jornadas cargadas para buscar el caballo.");
        }
        for (Jornada jornada : servicioJornada.getJornadas()) {// las carreras se cargan en Jornadas en la precarga de
                                                               // datos.
            if (jornada != null && jornada.getCarreras() != null) {
                for (Carrera carrera : jornada.getCarreras()) {
                    if (carrera.getRegistros() != null) {
                        for (Participante participante : carrera.getRegistros()) {// la carrera tiene los participantes
                                                                                  // con los caballos.
                            if (participante.getCaballo() != null
                                    && participante.getCaballo().getNumero() == numeroCaballo) {
                                return participante.getCaballo();
                            }
                        }
                    }
                }
            }
        }
        throw new HipodromoException("No se encontró el caballo con número: " + numeroCaballo);
    }

    public boolean buscarCaballoParticipaEnCarrera(Carrera carrera, int numeroCaballo) {
        if (carrera == null || carrera.getRegistros() == null) {
            return false;
        }

        return carrera.getRegistros().stream()
                .map(Participante::getCaballo)
                .filter(caballo -> caballo != null && caballo.getNumero() == numeroCaballo)
                .findFirst()
                .orElse(null) != null;
    }

    public List<Caballo> getCaballosCarrera(Carrera carrera) {
        // Devuelve la lista de caballos participantes en la carrera
        if (carrera == null || carrera.getRegistros() == null) {
            return new ArrayList<>();
        }
        return carrera.getRegistros().stream()
                .filter(participante -> participante != null && participante.getCaballo() != null)
                .map(Participante::getCaballo)
                .filter(caballo -> caballo != null)
                .toList();//listo los caballos que tiene el participante de la carrera.
    }

    public List<Carrera> getCarreras() throws HipodromoException {

        List<Carrera> carreras = new ArrayList<>();

        if (servicioJornada == null || servicioJornada.getJornadas() == null) {
            throw new HipodromoException("No hay jornadas cargadas para obtener las carreras.");
        }
        for (Jornada jornada : servicioJornada.getJornadas()) {
            if (jornada != null && jornada.getCarreras() != null) { // las carreras se cargan en Jornadas en la precarga de datos.
                carreras.addAll(jornada.getCarreras()); //agrega todas las carreras de la jornada a la lista de carreras que usa servicioCarrera para mostrar en el tablero de Administrador.
            }

        }
        return carreras;
    }
  public Participante obtenerParticipante(Caballo caballo, Carrera carrera) {
        if (caballo == null || carrera == null || carrera.getRegistros() == null) {
            return null;
        }
        return carrera.getRegistros().stream()
                .filter(participante -> participante != null && participante.esCaballoDelRegistro(caballo))
                .findFirst()
                .orElse(null);
    }
}
