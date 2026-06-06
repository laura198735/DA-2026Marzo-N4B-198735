package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.HipodromoException;

public abstract class Modalidad {
     
    public abstract double calcularCosto(double monto) throws HipodromoException;
    public abstract double calcularPago(Apuesta apuesta) throws HipodromoException;
   


    
}
