package ort.da.Obligatorio.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

class CarreraDividendosTest {

    @Test
    void calculaDividendoSegunLaConsignaConComisionDelDiezPorCiento() throws Exception {
        Jornada jornada = new Jornada(1, new Date());
        Carrera carrera = new Carrera("Carrera de ejemplo", jornada);

        Caballo caballoA = new Caballo("A", 1);
        Caballo caballoB = new Caballo("B", 2);
        Caballo caballoC = new Caballo("C", 3);

        Participante participanteA = new Participante(caballoA, carrera);
        Participante participanteB = new Participante(caballoB, carrera);
        Participante participanteC = new Participante(caballoC, carrera);

        carrera.getRegistros().add(participanteA);
        carrera.getRegistros().add(participanteB);
        carrera.getRegistros().add(participanteC);

        carrera.abrirCarrera();

        carrera.agregarApuesta(caballoA, new Apuesta(20000, new Simple(), participanteA));
        carrera.agregarApuesta(caballoB, new Apuesta(30000, new Simple(), participanteB));
        carrera.agregarApuesta(caballoC, new Apuesta(50000, new Simple(), participanteC));

        assertEquals(4.5, participanteA.getDividendoActual(), 0.0001);
        assertEquals(3.0, participanteB.getDividendoActual(), 0.0001);
        assertEquals(1.8, participanteC.getDividendoActual(), 0.0001);

        carrera.agregarApuesta(caballoB, new Apuesta(20000, new Simple(), participanteB));

        assertEquals(5.4, participanteA.getDividendoActual(), 0.0001);
        assertEquals(2.16, participanteB.getDividendoActual(), 0.0001);
        assertEquals(2.16, participanteC.getDividendoActual(), 0.0001);
    }

    @Test
    void pasaAEstableCuandoTodosLosDividendosSonValidos() throws Exception {
        Jornada jornada = new Jornada(1, new Date());
        Carrera carrera = new Carrera("Carrera de prueba", jornada);

        Caballo caballo1 = new Caballo("Caballo 1", 1);
        Caballo caballo2 = new Caballo("Caballo 2", 2);

        Participante participante1 = new Participante(caballo1, carrera);
        Participante participante2 = new Participante(caballo2, carrera);

        carrera.getRegistros().add(participante1);
        carrera.getRegistros().add(participante2);

        carrera.abrirCarrera();

        assertEquals("Abierta", carrera.obtenerNombreEstadoCarrera());

        carrera.agregarApuesta(caballo1, new Apuesta(1000, new Simple(), participante1));
        carrera.agregarApuesta(caballo2, new Apuesta(1000, new Simple(), participante2));

        assertTrue(carrera.todosLosDividendosSonValidos());
        assertEquals("Estable", carrera.obtenerNombreEstadoCarrera());
    }
}