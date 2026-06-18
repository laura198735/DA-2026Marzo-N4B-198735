package ort.da.Obligatorio;

import java.util.Date;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dominio.Simple;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.servicios.FachadaServicios;

public class DatosDePrueba {

    private DatosDePrueba() {
    }

    public static void cargar() {
        FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

        Caballo caballo1 = new Caballo("Relámpago", 3);
        Caballo caballo2 = new Caballo("Tornado", 7);
        Caballo caballo3 = new Caballo("Centella", 5);
        Caballo caballo4 = new Caballo("Rayo", 2);
        Caballo caballo5 = new Caballo("Trueno", 4);
        Caballo caballo6 = new Caballo("Vendaval", 6);

        long dia = 24 * 60 * 60 * 1000L;

        Jornada jornada1 = new Jornada(1, new Date(System.currentTimeMillis() - 30 * dia));
        Jornada jornada2 = new Jornada(2, new Date(System.currentTimeMillis() - 10 * dia));
        Jornada jornada3 = new Jornada(3, new Date(System.currentTimeMillis() - 10 * dia));
        Jornada jornada4 = new Jornada(4, new Date(System.currentTimeMillis() + 7 * dia));
        Jornada jornada5 = new Jornada(5, new Date(System.currentTimeMillis() - 7 * dia));
        Jornada jornada6 = new Jornada(6, new Date(System.currentTimeMillis()));
        Jornada jornada7 = new Jornada(7, new Date(System.currentTimeMillis() + 1 * dia));

        Carrera carrera1 = new Carrera("Carrera 1", jornada1);
        Carrera carrera2 = new Carrera("Carrera 2", jornada2);
        Carrera carrera3 = new Carrera("Carrera 3", jornada3);
        Carrera carrera4 = new Carrera("Carrera 4", jornada4);
        Carrera carrera5 = new Carrera("Carrera 5", jornada5);
        Carrera carrera6 = new Carrera("Carrera 6", jornada6);
        Carrera carrera7 = new Carrera("Carrera 7", jornada7);
        Carrera carrera8 = new Carrera("Premio Amanecer", jornada1);
        Carrera carrera9 = new Carrera("Premio del Prado", jornada1);
        Carrera carrera10 = new Carrera("Clásico Norte", jornada2);
        Carrera carrera11 = new Carrera("Gran Premio Sur", jornada2);
        Carrera carrera12 = new Carrera("Premio Las Piedras", jornada3);
        Carrera carrera13 = new Carrera("Handicap del Este", jornada3);
        Carrera carrera14 = new Carrera("Premio Primavera", jornada4);
        Carrera carrera15 = new Carrera("Clásico Costero", jornada4);
        Carrera carrera16 = new Carrera("Premio del Río", jornada5);
        Carrera carrera17 = new Carrera("Gran Premio Central", jornada5);
        Carrera carrera18 = new Carrera("Premio Matinal", jornada6);
        Carrera carrera19 = new Carrera("Clásico de la Tarde", jornada6);
        Carrera carrera20 = new Carrera("Premio Futuro", jornada7);
        Carrera carrera21 = new Carrera("Gran Premio Estelar", jornada7);

        carrera1.getRegistros().add(new Participante(caballo1, carrera1));
        carrera1.getRegistros().add(new Participante(caballo2, carrera1));
        carrera1.getRegistros().add(new Participante(caballo3, carrera1));
        carrera1.getRegistros().add(new Participante(caballo4, carrera1));
        carrera1.getRegistros().add(new Participante(caballo5, carrera1));
        carrera1.getRegistros().add(new Participante(caballo6, carrera1));

        carrera2.getRegistros().add(new Participante(caballo1, carrera2));
        carrera2.getRegistros().add(new Participante(caballo2, carrera2));
        carrera2.getRegistros().add(new Participante(caballo3, carrera2));
        carrera2.getRegistros().add(new Participante(caballo4, carrera2));
        carrera2.getRegistros().add(new Participante(caballo5, carrera2));
        carrera2.getRegistros().add(new Participante(caballo6, carrera2));

        carrera3.getRegistros().add(new Participante(caballo1, carrera3));
        carrera3.getRegistros().add(new Participante(caballo2, carrera3));
        carrera3.getRegistros().add(new Participante(caballo3, carrera3));
        carrera3.getRegistros().add(new Participante(caballo4, carrera3));
        carrera3.getRegistros().add(new Participante(caballo5, carrera3));
        carrera3.getRegistros().add(new Participante(caballo6, carrera3));

        carrera4.getRegistros().add(new Participante(caballo1, carrera4));
        carrera4.getRegistros().add(new Participante(caballo2, carrera4));
        carrera4.getRegistros().add(new Participante(caballo3, carrera4));
        carrera4.getRegistros().add(new Participante(caballo4, carrera4));
        carrera4.getRegistros().add(new Participante(caballo5, carrera4));
        carrera4.getRegistros().add(new Participante(caballo6, carrera4));

        carrera5.getRegistros().add(new Participante(caballo1, carrera5));
        carrera5.getRegistros().add(new Participante(caballo2, carrera5));
        carrera5.getRegistros().add(new Participante(caballo3, carrera5));
        carrera5.getRegistros().add(new Participante(caballo4, carrera5));
        carrera5.getRegistros().add(new Participante(caballo5, carrera5));
        carrera5.getRegistros().add(new Participante(caballo6, carrera5));

        carrera6.getRegistros().add(new Participante(caballo1, carrera6));
        carrera6.getRegistros().add(new Participante(caballo2, carrera6));
        carrera6.getRegistros().add(new Participante(caballo3, carrera6));
        carrera6.getRegistros().add(new Participante(caballo4, carrera6));
        carrera6.getRegistros().add(new Participante(caballo5, carrera6));
        carrera6.getRegistros().add(new Participante(caballo6, carrera6));

        carrera7.getRegistros().add(new Participante(caballo1, carrera7));
        carrera7.getRegistros().add(new Participante(caballo2, carrera7));
        carrera7.getRegistros().add(new Participante(caballo3, carrera7));
        carrera7.getRegistros().add(new Participante(caballo4, carrera7));
        carrera7.getRegistros().add(new Participante(caballo5, carrera7));
        carrera7.getRegistros().add(new Participante(caballo6, carrera7));

        agregarParticipantes(carrera8, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera9, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera10, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera11, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera12, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera13, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera14, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera15, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera16, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera17, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera18, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera19, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera20, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);
        agregarParticipantes(carrera21, caballo1, caballo2, caballo3, caballo4, caballo5, caballo6);

        jornada1.getCarreras().add(carrera1);
        jornada1.getCarreras().add(carrera8);
        jornada1.getCarreras().add(carrera9);
        jornada2.getCarreras().add(carrera2);
        jornada2.getCarreras().add(carrera10);
        jornada2.getCarreras().add(carrera11);
        jornada3.getCarreras().add(carrera3);
        jornada3.getCarreras().add(carrera12);
        jornada3.getCarreras().add(carrera13);
        jornada4.getCarreras().add(carrera4);
        jornada4.getCarreras().add(carrera14);
        jornada4.getCarreras().add(carrera15);
        jornada5.getCarreras().add(carrera5);
        jornada5.getCarreras().add(carrera16);
        jornada5.getCarreras().add(carrera17);
        jornada6.getCarreras().add(carrera6);
        jornada6.getCarreras().add(carrera18);
        jornada6.getCarreras().add(carrera19);
        jornada7.getCarreras().add(carrera7);
        jornada7.getCarreras().add(carrera20);
        jornada7.getCarreras().add(carrera21);

        try {
            precargarCarreraEstable(carrera1, fachadaServicios);
            precargarCarreraEstable(carrera2, fachadaServicios);
            precargarCarreraEstable(carrera3, fachadaServicios);
            precargarCarreraEstable(carrera4, fachadaServicios);

            fachadaServicios.agregarJornada(jornada1);
            fachadaServicios.agregarJornada(jornada2);
            fachadaServicios.agregarJornada(jornada3);
            fachadaServicios.agregarJornada(jornada4);
            fachadaServicios.agregarJornada(jornada5);
            fachadaServicios.agregarJornada(jornada6);
            fachadaServicios.agregarJornada(jornada7);
        } catch (Exception e) {
            System.out.println("Error al cargar jornadas: " + e.getMessage());
        }
    }

    private static void precargarCarreraEstable(Carrera carrera, FachadaServicios fachadaServicios)
            throws HipodromoException {
        carrera.abrirCarrera();

        for (Participante participante : carrera.getRegistros()) {
            Apuesta apuesta = new Apuesta(1, new Simple(), participante);
            carrera.agregarApuesta(participante.getCaballo(), apuesta);
            fachadaServicios.confirmarApuesta(apuesta);
        }
    }

    private static void agregarParticipantes(Carrera carrera, Caballo... caballos) {
        for (Caballo caballo : caballos) {
            carrera.getRegistros().add(new Participante(caballo, carrera));
        }
    }
}
