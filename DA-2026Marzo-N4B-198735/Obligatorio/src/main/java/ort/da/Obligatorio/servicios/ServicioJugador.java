package ort.da.Obligatorio.servicios;

import java.util.List;

import ort.da.Obligatorio.dominio.Apuesta;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.dominio.Jugador;
import ort.da.Obligatorio.excepciones.HipodromoException;



public class ServicioJugador {

  private List<Apuesta> apuestas;


  public ServicioJugador() {
    this.apuestas = new java.util.ArrayList<>();
  }

  public void realizarApuesta(Jugador jugador, Apuesta apuesta, String password) throws HipodromoException {
    if (jugador == null) {
      throw new HipodromoException("No hay un jugador logueado.");
    }
    if (apuesta == null) {
      throw new HipodromoException("La apuesta no puede ser nula.");
    }

    Credencial credencial = new Credencial(jugador.getNombreUsuario(), password);
    if (!jugador.validar(credencial)) {
      throw new HipodromoException("La contraseña ingresada no es correcta.");
    }

    // El servicio de aplicación coordina el caso de uso y delega en el agregado
    // para que aplique sus invariantes de saldo y registro interno.
    jugador.realizarApuesta(apuesta);
    apuestas.add(apuesta);

  }
    
}
