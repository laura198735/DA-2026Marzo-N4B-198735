package ort.da.Obligatorio.presentadores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dtos.CaballoDto;
import ort.da.Obligatorio.dtos.CarreraDto;
import ort.da.Obligatorio.servicios.FachadaServicios;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;

@RestController
@RequestMapping("/gestionar-carrera")
public class GestionarCarreraPresentador {

  private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

  @GetMapping()
  public Commands mostrarPantalla(HttpSession session) throws HipodromoException {
    if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
      return AuxiliarSesion.redirigirLoginAdmin();
    }
    Carrera carreraSeleccionada = (Carrera) session.getAttribute("carreraSeleccionada");
    Caballo caballoSeleccionado = (Caballo) session.getAttribute("caballoSeleccionado");

    return Commands.create(
        new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
        new Command("actualizarCaballos",
              CaballoDto.fromList(fachadaServicios.getCaballosCarrera(carreraSeleccionada))),
        new Command("mostrarCaballoSeleccionado", new CaballoDto(caballoSeleccionado)));
  }

  // CU Gestionar carreras - elegir carrera en vista
  @PostMapping("/seleccionar-carrera")
  public Commands seleccionarCarrera(@RequestParam("carreraId") int carreraNumero, HttpSession session)
      throws HipodromoException {

    if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
      return AuxiliarSesion.redirigirLoginAdmin();
    }
    try {
      Jornada jornadaActual = obtenerJornadaSeleccionada(session);
      Carrera carreraSeleccionada = fachadaServicios.buscarCarreraEnJornada(jornadaActual, carreraNumero);

      session.setAttribute("carreraSeleccionada", carreraSeleccionada);
      session.removeAttribute("caballoSeleccionado");

      return Commands.create(
          new Command("actualizarCaballos",
              CaballoDto.fromList(fachadaServicios.getCaballosCarrera(carreraSeleccionada))),
          new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
          new Command("mostrarCaballoSeleccionado", null));// saco seleccion del caballo anterior seleccionado.
    } catch (HipodromoException e) {
    return Commands.create(new Command("error", e.getMessage()));
    }
  }

  // CU Gestionar carreras - elegir caballo en vista
  @PostMapping("/seleccionar-caballo")
  public Commands seleccionarCaballo(@RequestParam("caballoId") int caballoNumero,
      HttpSession session) throws HipodromoException {

    if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
      return AuxiliarSesion.redirigirLoginAdmin();
    }
    // primero elijo carrera y desp caballo de la carrera
    Carrera carreraSeleccionada = (Carrera) session.getAttribute("carreraSeleccionada");

    if (carreraSeleccionada == null) {
      return Commands.create(
          new Command("error", "No hay carrera seleccionada”. Fin caso de uso"));
    }

    Caballo caballoSeleccionado = fachadaServicios.buscarCaballoPorNumero(caballoNumero);

    if (caballoSeleccionado == null) {
      return Commands.create(
          new Command("error", "No se encontró el caballo con número: " + caballoNumero));
    }

    session.setAttribute("caballoSeleccionado", caballoSeleccionado);

    return Commands.create(
        new Command("mensaje", "Carrera finalizada correctamente"),
        new Command("actualizarCaballos", CaballoDto.fromList(fachadaServicios.getCaballosCarrera(carreraSeleccionada))),
        new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
        new Command("mostrarCaballoSeleccionado", new CaballoDto(caballoSeleccionado)));
  }


  private Jornada obtenerJornadaSeleccionada(HttpSession session) throws HipodromoException {
    Jornada jornada = (Jornada) session.getAttribute("jornadaActual");

    if (jornada == null) {
        jornada = fachadaServicios.getJornadaActual();
        session.setAttribute("jornadaActual", jornada);
    }

    if (jornada == null) {
        throw new HipodromoException("No hay jornada seleccionada");
    }

    return jornada;
}
}