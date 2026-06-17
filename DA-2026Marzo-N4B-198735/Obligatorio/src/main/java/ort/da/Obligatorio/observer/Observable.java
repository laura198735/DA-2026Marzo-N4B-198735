package ort.da.Obligatorio.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    public enum Evento {
        AGENDA_ACTUALIZADA, USUARIOS_CONECTADOS_ACTUALIZADOS
    }

    private List<Observador> observadores;

    public Observable() {
        this.observadores = new ArrayList<>();
    }

    public void subscribir(Observador observador) {
        if(!observadores.contains(observador))
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
