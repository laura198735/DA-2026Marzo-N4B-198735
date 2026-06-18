package ort.da.Obligatorio.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import ort.da.Obligatorio.dominio.Administrador;
import ort.da.Obligatorio.dominio.Credencial;
import ort.da.Obligatorio.excepciones.AutenticacionException;

class ServicioAutenticacionTest {

    @Test
    void noPermiteDosLoginsDelMismoAdministradorAlMismoTiempo() throws AutenticacionException {
        Administrador administrador = new Administrador("a1", "a1");
        ServicioAutenticacion servicio = new ServicioAutenticacion(List.of(administrador), List.of());

        servicio.autenticarAdministrador(new Credencial("a1", "a1"));

        AutenticacionException exception = assertThrows(AutenticacionException.class,
                () -> servicio.autenticarAdministrador(new Credencial("a1", "a1")));

        assertEquals("El administrador ya tiene una sesión activa", exception.getMessage());
    }

    @Test
    void luegoDelLogoutPermiteVolverALoguearse() throws AutenticacionException {
        Administrador administrador = new Administrador("a1", "a1");
        ServicioAutenticacion servicio = new ServicioAutenticacion(List.of(administrador), List.of());

        servicio.autenticarAdministrador(new Credencial("a1", "a1"));
        servicio.cerrarSesionAdministrador(administrador);

        servicio.autenticarAdministrador(new Credencial("a1", "a1"));

        assertEquals(1, servicio.getLogins().size());
    }
}