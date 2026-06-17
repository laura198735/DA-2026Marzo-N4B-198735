package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

//patron strategy: (cada modalidad de apuesta tiene su propia forma de calcular el costo y el pago)
public abstract class Modalidad {
    public abstract String getNombre();

    public abstract double calcularCosto(double monto) throws HipodromoException;

    public abstract double calcularPago(double monto, Participante participante) throws HipodromoException;

}
