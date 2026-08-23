package ort.da.Obligatorio.servicios;

import java.util.List;

import ort.da.Obligatorio.dominio.Apuesta;



public class ServicioJugador {

  private List<Apuesta> apuestas;


  public ServicioJugador() {
    this.apuestas = new java.util.ArrayList<>();
  }

  public void realizarApuesta(Apuesta apuesta) {
    apuestas.add(apuesta);

  }
    
}
