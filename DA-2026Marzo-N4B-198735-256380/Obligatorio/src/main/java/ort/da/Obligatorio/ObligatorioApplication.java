package ort.da.Obligatorio;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Dividendo;
//import ort.da.Obligatorio.dominio.Estado;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.dominio.Participante;

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

			Jornada jornada1 = new Jornada();
			Carrera carrera1 = new Carrera("Gran Premio Inaugural", new Date(System.currentTimeMillis()), 1000.0, null);
			Carrera carrera2 = new Carrera("Clásico de Verano", new Date(System.currentTimeMillis()), 1200.0, null);

			Dividendo dividendo = new Dividendo();
			// EstadoCarrera type expected by Participante constructor — provide a simple subclass here
			//EstadoCarrera estadoCarrera = new EstadoCarrera();

			Participante rp1 = new Participante(caballo1, carrera1, dividendo);
			Participante rp2 = new Participante(caballo2, carrera1, dividendo);
			Participante rp3 = new Participante(caballo2, carrera2, dividendo);
			Participante rp4 = new Participante(caballo3, carrera2, dividendo);

			List<Object> datosPrueba = Arrays.asList(
				administrador1,
				administrador2,
				jugador1,
				jugador2,
				caballo1,
				caballo2,
				caballo3,
				jornada1,
				carrera1,
				carrera2,
				rp1,
				rp2,
				rp3,
				rp4
			);

			System.out.println("Datos de prueba creados exitosamente: " + datosPrueba.size() + " objetos.");
			System.out.println("Administrador 1: a1 / a1 / Usuario Administrador");
			System.out.println("Administrador 2: a2 / a2 / Admin Secundario");
			System.out.println("Jugador 1: j1 / j1 / Usuario Jugador / saldo 2000");
			System.out.println("Jugador 2: j2 / j2 / Jugador Prueba / saldo 3500");
			System.out.println("Comision del hipodromo: 10%");
			System.out.println("Carrera 1: Gran Premio Inaugural con Relámpago #3 y Tornado #7");
			System.out.println("Carrera 2: Clásico de Verano con Tornado #2 y Centella #5");

		} catch (Exception e) {
			System.out.println("Error al crear datos de prueba: " + e.getMessage());
		}
	}


}
