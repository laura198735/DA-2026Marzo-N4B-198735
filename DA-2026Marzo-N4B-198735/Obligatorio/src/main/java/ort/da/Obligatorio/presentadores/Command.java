package ort.da.Obligatorio.presentadores;

import lombok.Getter;

/**
 * Representa un comando individual que se envía al frontend.
 * Cada comando tiene un identificador (id) y un parámetro (dato asociado).
 * 
 * Antes llamado "Respuesta" - renombrado para reflejar mejor su propósito
 * como parte del patrón Command.
 */
public class Command {
    @Getter
    private String id;
    @Getter
    private Object parametro;

    public Command(String id, Object parametro) {
        this.id = id;
        this.parametro = parametro;
    }

    public Command() {
    }
}
