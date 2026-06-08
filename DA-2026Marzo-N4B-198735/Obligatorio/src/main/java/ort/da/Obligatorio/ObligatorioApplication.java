package ort.da.Obligatorio;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.servicios.FachadaServicios;

@SpringBootApplication
public class ObligatorioApplication {

	public static void main(String[] args) {
		crearDatosPrueba();
		SpringApplication.run(ObligatorioApplication.class, args);
	}

	public static void crearDatosPrueba() {
		try {
			Administrador administrador1 = new Administrador("a1", "a1");
			Administrador administrador2 = new Administrador("a2", "a2");

			Jugador jugador1 = new Jugador("j1", "j1");
			Jugador jugador2 = new Jugador("j2", "j2");

			Caballo caballo1 = new Caballo("Relámpago", 3);
			Caballo caballo2 = new Caballo("Tornado", 7);
			Caballo caballo3 = new Caballo("Centella", 5);
			Caballo caballo4 = new Caballo("Rayo", 2);
			Caballo caballo5 = new Caballo("Trueno", 4);
			Caballo caballo6 = new Caballo("Vendaval", 6);

			long dia = 24 * 60 * 60 * 1000L;

			Jornada jornada1 = new Jornada(1, new Date(System.currentTimeMillis() - 30 * dia));
			Jornada jornada2 = new Jornada(2, new Date(System.currentTimeMillis() - 10 * dia));
			Jornada jornada3 = new Jornada(3, new Date(System.currentTimeMillis() + 7 * dia));
			Jornada jornada4 = new Jornada(4, new Date(System.currentTimeMillis() - 7 * dia));
			Jornada jornada5 = new Jornada(5, new Date(System.currentTimeMillis() - 1 * dia));
			Jornada jornada6 = new Jornada(6, new Date(System.currentTimeMillis()));
			Jornada jornada7 = new Jornada(7, new Date(System.currentTimeMillis() + 1 * dia));
			Jornada jornada8 = new Jornada(8, new Date(System.currentTimeMillis() + 2 * dia));
			Jornada jornada9 = new Jornada(9, new Date(System.currentTimeMillis() + 3 * dia));
			Jornada jornada10 = new Jornada(10, new Date(System.currentTimeMillis() + 30 * dia));

			Carrera carrera1 = new Carrera(1, "Velocidad Suprema", jornada1);
			Carrera carrera2 = new Carrera(2, "Gran Premio Oriental", jornada1);
			Carrera carrera3 = new Carrera(3, "Copa Montevideo", jornada1);

			Carrera carrera4 = new Carrera(4, "Desafío del Sur", jornada2);
			Carrera carrera5 = new Carrera(5, "Clásico de los Campeones", jornada2);
			Carrera carrera6 = new Carrera(6, "Trofeo Libertad", jornada2);

			Carrera carrera7 = new Carrera(7, "Gran Derby Nacional", jornada3);
			Carrera carrera8 = new Carrera(8, "Premio Primavera", jornada3);
			Carrera carrera9 = new Carrera(9, "Carrera de las Estrellas", jornada3);

			Carrera carrera10 = new Carrera(10, "Desafío del Hipódromo", jornada4);
			Carrera carrera11 = new Carrera(11, "Clásico Invierno", jornada4);
			Carrera carrera12 = new Carrera(12, "Premio Relámpago", jornada4);

			Carrera carrera13 = new Carrera(13, "Gran Premio Verano", jornada5);
			Carrera carrera14 = new Carrera(14, "Copa de Oro", jornada5);
			Carrera carrera15 = new Carrera(15, "Clásico del Río", jornada5);

			Carrera carrera16 = new Carrera(16, "Premio Centenario", jornada6);
			Carrera carrera17 = new Carrera(17, "Carrera de Campeones", jornada6);
			Carrera carrera18 = new Carrera(18, "Desafío del Este", jornada6);

			Carrera carrera19 = new Carrera(19, "Trofeo Victoria", jornada7);
			Carrera carrera20 = new Carrera(20, "Gran Premio Internacional", jornada7);
			Carrera carrera21 = new Carrera(21, "Premio Horizonte", jornada7);

			Carrera carrera22 = new Carrera(22, "Copa del Plata", jornada8);
			Carrera carrera23 = new Carrera(23, "Clásico Federal", jornada8);
			Carrera carrera24 = new Carrera(24, "Premio Eclipse", jornada8);

			Carrera carrera25 = new Carrera(25, "Gran Premio Uruguay", jornada9);
			Carrera carrera26 = new Carrera(26, "Desafío de Campeones", jornada9);
			Carrera carrera27 = new Carrera(27, "Premio Tradición", jornada9);

			Carrera carrera28 = new Carrera(28, "Copa Final", jornada10);
			Carrera carrera29 = new Carrera(29, "Clásico Clausura", jornada10);
			Carrera carrera30 = new Carrera(30, "Gran Premio Fin de Temporada", jornada10);

			agregarCarreras(jornada1, carrera1, carrera2, carrera3);
			agregarCarreras(jornada2, carrera4, carrera5, carrera6);
			agregarCarreras(jornada3, carrera7, carrera8, carrera9);
			agregarCarreras(jornada4, carrera10, carrera11, carrera12);
			agregarCarreras(jornada5, carrera13, carrera14, carrera15);
			agregarCarreras(jornada6, carrera16, carrera17, carrera18);
			agregarCarreras(jornada7, carrera19, carrera20, carrera21);
			agregarCarreras(jornada8, carrera22, carrera23, carrera24);
			agregarCarreras(jornada9, carrera25, carrera26, carrera27);
			agregarCarreras(jornada10, carrera28, carrera29, carrera30);

			agregarParticipantes(carrera1, caballo1, caballo2);
			agregarParticipantes(carrera2, caballo2, caballo3);
			agregarParticipantes(carrera3, caballo1, caballo3);
			agregarParticipantes(carrera4, caballo2, caballo3);
			agregarParticipantes(carrera5, caballo1, caballo2);
			agregarParticipantes(carrera6, caballo3, caballo1);
			agregarParticipantes(carrera7, caballo2, caballo3);
			agregarParticipantes(carrera8, caballo1, caballo2);
			agregarParticipantes(carrera9, caballo3, caballo1);
			agregarParticipantes(carrera10, caballo2, caballo3);
			agregarParticipantes(carrera11, caballo1, caballo2);
			agregarParticipantes(carrera12, caballo3, caballo1);
			agregarParticipantes(carrera13, caballo2, caballo3);
			agregarParticipantes(carrera14, caballo1, caballo2);
			agregarParticipantes(carrera15, caballo3, caballo1);
			agregarParticipantes(carrera16, caballo2, caballo3);
			agregarParticipantes(carrera17, caballo1, caballo2);
			agregarParticipantes(carrera18, caballo3, caballo1);
			agregarParticipantes(carrera19, caballo2, caballo3);
			agregarParticipantes(carrera20, caballo1, caballo2);
			agregarParticipantes(carrera21, caballo3, caballo1);
			agregarParticipantes(carrera22, caballo2, caballo3);
			agregarParticipantes(carrera23, caballo1, caballo2);
			agregarParticipantes(carrera24, caballo3, caballo1);
			agregarParticipantes(carrera25, caballo2, caballo3);
			agregarParticipantes(carrera26, caballo1, caballo2);
			agregarParticipantes(carrera27, caballo3, caballo1);
			agregarParticipantes(carrera28, caballo2, caballo3);
			agregarParticipantes(carrera29, caballo1, caballo2);
			agregarParticipantes(carrera30, caballo3, caballo1);

			List<Jornada> jornadas = FachadaServicios.getInstancia().getJornadas();
			jornadas.clear();

			jornadas.add(jornada1);
			jornadas.add(jornada2);
			jornadas.add(jornada3);
			jornadas.add(jornada4);
			jornadas.add(jornada5);
			jornadas.add(jornada6);
			jornadas.add(jornada7);
			jornadas.add(jornada8);
			jornadas.add(jornada9);
			jornadas.add(jornada10);

			System.out.println("Datos de prueba creados correctamente.");
			System.out.println("Admin: a1 / a1");
			System.out.println("Admin: a2 / a2");
			System.out.println("Jugador: j1 / j1");
			System.out.println("Jugador: j2 / j2");

		} catch (Exception e) {
			System.err.println("Error al crear datos de prueba: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void agregarCarreras(Jornada jornada, Carrera... carreras) {
		for (Carrera carrera : carreras) {
			jornada.getCarreras().add(carrera);
		}
	}

	private static void agregarParticipantes(Carrera carrera, Caballo... caballos) {
		for (Caballo caballo : caballos) {
			Participante participante = new Participante(caballo, carrera);
			carrera.getRegistros().add(participante);
		}
	}
}




