package ort.da.Obligatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObligatorioApplication {

	public static void main(String[] args) {
		DatosDePrueba.cargar();
		SpringApplication.run(ObligatorioApplication.class, args);
	}
}