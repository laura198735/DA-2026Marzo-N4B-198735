package ort.da.Obligatorio.dominio;

import ort.da.Obligatorio.excepciones.ObligatorioException;

public abstract class Modalidad {
     
    public abstract double calcularCosto(double monto) throws ObligatorioException;
    public abstract double calcularPago(Apuesta apuesta) throws ObligatorioException;
   


    
}
