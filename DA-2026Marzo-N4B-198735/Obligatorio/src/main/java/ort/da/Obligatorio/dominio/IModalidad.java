package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

//patron strategy: (cada modalidad de apuesta tiene su propia forma de calcular el costo y el pago)
public interface IModalidad {

    String getNombre();

    double calcularCosto(double monto) throws HipodromoException;

    double calcularPago(double monto, Participante participante) throws HipodromoException;

}
