package ort.da.Obligatorio.servicios;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import ort.da.Obligatorio.dominio.Carrera;

public class ServicioTableroAdmin {
    private Date fechaJornada = new Date(System.currentTimeMillis());
    private double totalApostado;
    private double totalPagado;
    private double comisionesCobradas;
    private double ganancias;// apostado - pagado
    private int cantidadCarreras;
    private int cantidadCarrerasPendientes;
    private int cantidadCarrerasFinalizadas;
    private List<Carrera> carreraspendientes = new ArrayList<>();
    private List<Carrera> carrerasfinalizadas = new ArrayList<>();






    
}
