package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.EstadoException;

public class Estable implements IEstadoCarrera {

    public Estable() {
    }

    @Override
    public void abrirCarrera(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede abrir una carrera en estado estable"); // Implementación específica para
                                                                                      // la clase Estable

    }

    @Override
    public int asignarGanador(Carrera carrera) throws EstadoException {
        throw new EstadoException("No se puede asignar un ganador a una carrera en estado estable");
    }

    @Override
    public void cerrarCarrera(Carrera carrera) throws EstadoException {
            throw new EstadoException("No se puede cerrar una carrera en estado estable"); // Implementación específica para la clase Estable
        
    }

    /**
     * Un dividendo es válido únicamente cuando:
     * La cantidad de apuestas al caballo es mayor a 0.
     * El dividendo es mayor a 1.
     */

    @Override
    public boolean puedeApostar(Carrera carrera) {
        if (carrera == null)
            return false;
        final double COMISION = 0.10; // 10% de comisión del hipódromo
        if (carrera.getRegistros() == null || carrera.getRegistros().isEmpty())
            return false;

        for (Participante registro : carrera.getRegistros()) {
            double dividendo = registro.calcularDividendo(COMISION);
            if (dividendo > 1.0) {
                carrera.setEstadoCarrera(new Abierta());
                return true;
            }
        }
        return false;
    }

}
