package ort.da.Obligatorio.dominio;

import net.bytebuddy.implementation.bind.annotation.SuperMethod;

/**
 * ModalidadFactory
 */
public class ModalidadFactory {

    public static IModalidad getModalidad(String modalidadNombre) {
        if (modalidadNombre.equalsIgnoreCase("Simple")) {
            return new Simple();
        } else if (modalidadNombre.equalsIgnoreCase("Super")) {
            return new Super();
        } else if (modalidadNombre.equalsIgnoreCase("Triple")) {
            return new Triple();
        } else {
            throw new IllegalArgumentException("Modalidad no válida: " + modalidadNombre);
        }
    }

}
