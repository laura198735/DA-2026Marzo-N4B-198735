package ort.da.Obligatorio.servicios;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Finalizada;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.excepciones.HipodromoException;

@Getter
public class ServicioJornada {

    private List<Jornada> jornadas;

    // Lista de jornadas para gestión de carreras
    public ServicioJornada() {
        this.jornadas = new ArrayList<>();
    }

    public List<Jornada> getJornadas() {
        return jornadas;
    }

    public Jornada getJornadaActual() {
        Date hoy = Jornada.truncarHora(new Date());

        for (Jornada jornada : jornadas) {
            if (jornada == null || jornada.getDia() == null)
                continue;
            Date diaJ = Jornada.truncarHora(jornada.getDia());
            if (diaJ == null)
                continue;
            // si la fecha de hoy es igual o posterior a la fecha de la jornada (solo fecha), entonces es la jornada actual
            if (!hoy.before(diaJ)) {
                return jornada;
            }
        }

        return null;
    }
    

    // las jornadas se agregan en orden cronologico inverso
    public void agregar(Jornada jornada) {
        this.jornadas.add(jornada);
    }

    // * • Cantidad de carreras que faltan por correr en la jornada actual

    public int getCantidadProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getCantidadProximasCarrerasJornada();
    }

    public double getTotalApostado(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getTotalApostado();
    }

    public double getTotalPagado(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getTotalPagado();
    }

    public double getTotalComisionesJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        final double comision = 0.10;
        return jornada.getTotalApostado() * comision;
    }

    public double getBalanceJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        double totalA = jornada.getTotalApostado();
        double totalP = jornada.getTotalPagado();
        double com = getTotalComisionesJornada(jornada);
        return totalA - totalP - com;
    }

    public int getCantidadCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        return jornada.getCarreras() == null ? 0 : jornada.getCarreras().size();
    }


    public int getCantidadCarrerasFinalizadasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        if (jornada.getCarreras() == null)
            return 0;
        int c = 0;
        for (Carrera ca : jornada.getCarreras()) {
            if (ca.getEstadoCarrera() instanceof Finalizada)
                c++;
        }
        return c;
    }
    public int getCantidadCProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        if (jornada.getCarreras() == null)
            return 0;
        int c = 0;
        for (Carrera ca : jornada.getCarreras()) {
            if (!(ca.getEstadoCarrera() instanceof Finalizada))
                c++;
        }
        return c;
    }
    public List<Carrera> getResultadosCarrerasJornadaOrdenadas(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        List<Carrera> res = new ArrayList<>();
        if (jornada.getCarreras() == null)
            return res;
        for (Carrera ca : jornada.getCarreras()) {
            if (ca.getEstadoCarrera() instanceof Finalizada)
                res.add(ca);
        }
        res.sort((a, b) -> Integer.compare(b.getNumeroCarrera(), a.getNumeroCarrera()));
        return res;
    }

    public List<Carrera> getListaProximasCarrerasJornada(Jornada jornada) throws HipodromoException {
        if (jornada == null)
            throw new HipodromoException("No hay jornada seleccionada");
        List<Carrera> res = new ArrayList<>();
        if (jornada.getCarreras() == null)
            return res;
        for (Carrera ca : jornada.getCarreras()) {
            if (!(ca.getEstadoCarrera() instanceof Finalizada))
                res.add(ca);
        }
        return res;
    }



   

//auxiliares
    public Jornada getJornadaPorFecha(Date fechaSeleccionada) {
        try {
            if (fechaSeleccionada == null) {
                return null;
            }

            Date fechaNormalizada = Jornada.truncarHora(fechaSeleccionada);
            List<Jornada> jornadas = getJornadas();
            if (jornadas != null) {
                for (Jornada j : jornadas) {
                    if (j.getDia() != null && Jornada.truncarHora(j.getDia()).equals(fechaNormalizada)) {
                        return j;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la jornada por fecha: " + e.getMessage());
        }
    }

}