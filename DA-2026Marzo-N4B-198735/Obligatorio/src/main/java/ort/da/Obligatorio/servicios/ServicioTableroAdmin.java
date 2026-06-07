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




/* Fecha de la jornada actual (inicialmente es la jornada de la fecha actual o la más próxima anterior si no hay 
jornada en el día, luego podrá ser cambiada por el usuario)  
• Total apostado en la jornada actual 
• Total pagado en la jornada actual  
• Total de comisiones cobradas en la jornada actual 
• Balance general de la jornada (total apostado – total pagado) 
• Cantidad de carreras de la jornada actual 
• Cantidad de carreras Finalizadas en la jornada actual 
• Cantidad de carreras que faltan por correr en la jornada actual 
 */

    
}
