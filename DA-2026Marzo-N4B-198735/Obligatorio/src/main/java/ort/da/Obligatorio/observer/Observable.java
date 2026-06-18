package ort.da.Obligatorio.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    public enum Evento {
        //CARRERA 
        ESTADO_CARRERA_MODIFICADO, CARRERA_DIVIDENDO_ACTUALIZADO, APUESTA_AGREGADA, 
        CARRERA_TOTAL_PAGADO_ACTUALIZADO, CARRERA_TOTAL_APOSTADO_ACTUALIZADO,CARRERA, ESTADO_CARRERA_FINALIZADO, CARRERA_DIVIDENDO_FINAL_ACTUALIZADO,

        //ACCESO
        ADMINISTRADOR_CONECTADO, JUGADOR_CONECTADO,
       

    }

    private List<Observador> observadores;

    public Observable() {
        this.observadores = new ArrayList<>();
    }

    public void subscribir(Observador observador) {
        if (!observadores.contains(observador))
            this.observadores.add(observador);
    }

    public void desubscribir(Observador observador) {
        this.observadores.remove(observador);
    }

    public void notificar(Object evento) {
        for (Observador observador : observadores) {
            observador.actualizar(this, evento);
        }
    }

}
