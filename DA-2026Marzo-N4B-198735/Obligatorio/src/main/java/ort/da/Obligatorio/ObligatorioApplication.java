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

	/*
	 * 3 carreras con fecha del día actual en estado Definida y sin apuestas a
	 * ningún caballo.
	 * o 2 carreras con fecha de una semana anterior a la fecha actual en estado
	 * Cerrada, con apuestas para
	 * todos los caballos participantes (entre 10 y 20 apuestas para cada uno
	 * realizadas por jugadores
	 * diferentes).
	 * o 1 carrera con fecha de una semana posterior en estado Definida

	*/
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

			Jornada jornada1 = new Jornada(1, new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada2 = new Jornada(9, new Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada3 = new Jornada(2, new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada4 = new Jornada(3, new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada5 = new Jornada(4, new Date(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada6 = new Jornada(5, new Date(System.currentTimeMillis())); // hoy
			Jornada jornada7 = new Jornada(6, new Date(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada8 = new Jornada(7, new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada9 = new Jornada(8, new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L)); 
			Jornada jornada10 = new Jornada(10, new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000L)); 

			Carrera carrera1 = new Carrera(1, "Gran Premio Inaugural", jornada1);
			Carrera carrera2 = new Carrera(2, "Clásico de Verano", jornada2);
			Carrera carrera3 = new Carrera(3, "Carrera Internacional", jornada3);
			Carrera carrera4 = new Carrera(4, "Carrera de Hoy", jornada4);
			Carrera carrera5 = new Carrera(5, "Carrera de Mañana", jornada5);
			Carrera carrera6 = new Carrera(6, "Carrera de Interfaces", jornada6);
			Carrera carrera7 = new Carrera(7, "Carrera de Patrones", jornada7);
			Carrera carrera8 = new Carrera(8, "Carrera de Dentro de Cuatro Días", jornada8);
			Carrera carrera9 = new Carrera(9, "Carrera de Dentro de Cinco Días", jornada9);

			Carrera carrera10 = new Carrera(10, "Carrera del futuro", jornada10);

			// agrega carreras a las jornadas
			jornada1.getCarreras().add(carrera1);
			jornada2.getCarreras().add(carrera2);
			jornada3.getCarreras().add(carrera3);
			jornada4.getCarreras().add(carrera4);
			jornada5.getCarreras().add(carrera5);
			jornada6.getCarreras().add(carrera6);
			jornada7.getCarreras().add(carrera7);
			jornada8.getCarreras().add(carrera8);
			jornada9.getCarreras().add(carrera9);
			jornada10.getCarreras().add(carrera10);

			

			Participante rp1 = new Participante(caballo1, carrera1, 3.0);
			Participante rp2 = new Participante(caballo2, carrera1, .0);
			Participante rp3 = new Participante(caballo2, carrera2, 0.0);
			Participante rp4 = new Participante(caballo3, carrera2, 0.0);
			Participante rp5 = new Participante(caballo1, carrera3, 3.0);
			Participante rp6 = new Participante(caballo3, carrera4, 0.0);
			Participante rp7 = new Participante(caballo2, carrera5, 0.0);
			Participante rp8 = new Participante(caballo3, carrera6, 0.0);
			Participante rp9 = new Participante(caballo1, carrera7, 3.0);
			Participante rp10 = new Participante(caballo2, carrera8, 0.0);
			Participante rp11 = new Participante(caballo3, carrera9, 0.0);
			Participante rp12 = new Participante(caballo1, carrera10, 3.0);

			List<Object> datosPrueba = Arrays.asList(
					administrador1,
					administrador2,
					jugador1,
					jugador2,
					caballo1,
					caballo2,
					caballo3,
					caballo4,
					caballo5,
					caballo6,
					jornada1,
					carrera1,
					carrera2,
					rp1,
					rp2,
					rp3,
					rp4,
					rp5,
					rp6,
					rp7,
					rp8,
					rp9,
					rp10,
					rp11,
					rp12);

			/*System.out.println("Datos de prueba creados exitosamente: " + datosPrueba.size() + " objetos.");
			System.out.println("Administrador 1: a1 / a1 / Usuario Administrador");
			System.out.println("Administrador 2: a2 / a2 / Admin Secundario");
			System.out.println("Jugador 1: j1 / j1 / Usuario Jugador / saldo 2000");
			System.out.println("Jugador 2: j2 / j2 / Jugador Prueba / saldo 3500");
			System.out.println("Comision del hipodromo: 10%");
			System.out.println("Carrera 1: Gran Premio Inaugural con Relámpago #3 y Tornado #7");

			System.out.println("Carrera 2: Clásico de Verano con Tornado #2 y Centella #5");
			System.out.println("Carrera 3: Carrera del Futuro con Relámpago #3 y Centella #5");
			System.out.println("Carrera 4: Carrera de Hoy con Tornado #7 y Centella #5");*/

			// Agregar jornadas al sistema
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada1);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada2);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada3);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada4);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada5);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada6);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada7);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada8);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada9);
			ort.da.Obligatorio.servicios.FachadaServicios.getInstancia().getJornadas().add(jornada10);
		

			jornada1.getCarreras().add(carrera1);
			jornada2.getCarreras().add(carrera2);
			jornada3.getCarreras().add(carrera3);
			jornada4.getCarreras().add(carrera4);
			jornada5.getCarreras().add(carrera5);
			jornada6.getCarreras().add(carrera6);
			jornada7.getCarreras().add(carrera7);
			jornada8.getCarreras().add(carrera8);
			jornada9.getCarreras().add(carrera9);
			jornada10.getCarreras().add(carrera10);

			carrera1.getRegistros().add(rp1);
			carrera1.getRegistros().add(rp2);
			carrera2.getRegistros().add(rp3);
			carrera2.getRegistros().add(rp4);
			carrera3.getRegistros().add(rp5);
			carrera4.getRegistros().add(rp6);
			carrera5.getRegistros().add(rp7);
			carrera6.getRegistros().add(rp8);
			carrera7.getRegistros().add(rp9);
			carrera8.getRegistros().add(rp10);
			carrera9.getRegistros().add(rp11);
			carrera10.getRegistros().add(rp12);		


		} catch (Exception e) {
				System.err.println("Error al crear datos de prueba: " + e.getMessage());	
		}
	}

}





