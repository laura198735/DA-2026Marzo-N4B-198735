package ort.da.Obligatorio;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;

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
/* 3 carreras con fecha del día actual en estado Definida y sin apuestas a ningún caballo. 
o 2 carreras con fecha de una semana anterior a la fecha actual en estado Cerrada, con apuestas para 
todos los caballos participantes (entre 10 y 20 apuestas para cada uno realizadas por jugadores 
diferentes).  
o 1 carrera con fecha de una semana posterior en estado Definida*/ 
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
			Jornada jornada2 = new Jornada(1, new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L)); // hace una semana
			Jornada jornada3 = new Jornada(2, new Date(System.currentTimeMillis() - 1* 24 * 60 * 60 * 1000L)); //hace 1 dia
			Jornada jornada4 = new Jornada(3, new Date(System.currentTimeMillis())); // hoy		
			Carrera carrera1 = new Carrera(1,"Gran Premio Inaugural", jornada1);
			Carrera carrera2 = new Carrera(2,"Clásico de Verano", jornada2);
			Carrera carrera3 = new Carrera(3,"Carrera del Futuro", jornada3);
			Carrera carrera4 = new Carrera(4,"Carrera de Hoy", jornada4);

			// EstadoCarrera type expected by Participante constructor — provide a simple subclass here
			//EstadoCarrera estadoCarrera = new EstadoCarrera()

			Participante rp1 = new Participante(caballo1, carrera1,  3.0);
			Participante rp2 = new Participante(caballo2, carrera1, .0);
			Participante rp3 = new Participante(caballo2, carrera2,  0.0);
			Participante rp4 = new Participante(caballo3, carrera2, 0.0);
			Participante rp5 = new Participante(caballo1, carrera3,  3.0);
			Participante rp6 = new Participante(caballo3, carrera4, 0.0);

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
				rp4,
				rp5,
				rp6
			);

			System.out.println("Datos de prueba creados exitosamente: " + datosPrueba.size() + " objetos.");
			System.out.println("Administrador 1: a1 / a1 / Usuario Administrador");
			System.out.println("Administrador 2: a2 / a2 / Admin Secundario");
			System.out.println("Jugador 1: j1 / j1 / Usuario Jugador / saldo 2000");
			System.out.println("Jugador 2: j2 / j2 / Jugador Prueba / saldo 3500");
			System.out.println("Comision del hipodromo: 10%");
			System.out.println("Carrera 1: Gran Premio Inaugural con Relámpago #3 y Tornado #7");

			System.out.println("Carrera 2: Clásico de Verano con Tornado #2 y Centella #5");
			System.out.println("Carrera 3: Carrera del Futuro con Relámpago #3 y Centella #5");
			System.out.println("Carrera 4: Carrera de Hoy con Tornado #7 y Centella #5");

			// Agregar jornada de prueba al sistema
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada1);

		} catch (Exception e) {
			System.out.println("Error al crear datos de prueba: " + e.getMessage());
		}
	}


}
