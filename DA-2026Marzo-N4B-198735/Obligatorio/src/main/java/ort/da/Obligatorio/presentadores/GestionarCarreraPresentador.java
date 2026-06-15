package ort.da.Obligatorio.presentadores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.Obligatorio.dominio.Caballo;
import ort.da.Obligatorio.dominio.Carrera;
import ort.da.Obligatorio.dominio.Jornada;
import ort.da.Obligatorio.dominio.Participante;
import ort.da.Obligatorio.dtos.CaballoDto;
import ort.da.Obligatorio.dtos.CarreraDto;
import ort.da.Obligatorio.dtos.ParticipanteDto;
import ort.da.Obligatorio.excepciones.HipodromoException;
import ort.da.Obligatorio.presentadores.auxiliar.AuxiliarSesion;
import ort.da.Obligatorio.servicios.FachadaServicios;

@RestController
@RequestMapping("/gestionar-carrera")
public class GestionarCarreraPresentador {

  private final FachadaServicios fachadaServicios = FachadaServicios.getInstancia();

  @GetMapping()
  public Commands mostrarPantalla(@RequestParam(value = "numeroCarrera", required = false) Integer numeroCarrera,
      HttpSession session) throws HipodromoException {

    if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
      return AuxiliarSesion.redirigirLoginAdmin();
    }

    if (numeroCarrera == null || numeroCarrera <= 0) {
      return Commands.create(new Command("error", "No se recibio el numero de carrera."));
    }

    Carrera carreraSeleccionada;

    try {
      Jornada jornadaActual = obtenerJornadaSeleccionada(session);
      carreraSeleccionada = fachadaServicios.buscarCarreraEnJornada(jornadaActual, numeroCarrera);
    } catch (HipodromoException e) {
      return Commands.create(new Command("error", e.getMessage()));
    }

    if (carreraSeleccionada == null) {
      return Commands.create(new Command("error", "No se encontro la carrera con numero: " + numeroCarrera));
    }

    session.setAttribute("carreraSeleccionada", carreraSeleccionada);
    session.removeAttribute("caballoSeleccionado");

    return Commands.create(
        new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
        new Command("actualizarCaballos", ParticipanteDto.fromCarrera(carreraSeleccionada)),//desde participanteDto para enviar el dividendo de cada caballo.
        new Command("mostrarCaballoSeleccionado", null));
  }

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
          new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
          new Command("actualizarCaballos", ParticipanteDto.fromCarrera(carreraSeleccionada)),
          new Command("mostrarCaballoSeleccionado", null));
    } catch (HipodromoException e) {
      return Commands.create(new Command("error", e.getMessage()));
    }
  }

  @PostMapping("/seleccionar-caballo")
  public Commands seleccionarCaballo(@RequestParam("caballoId") int caballoNumero,
      HttpSession session) throws HipodromoException {

    if (!AuxiliarSesion.usuarioAdministradorLogueado(session)) {
      return AuxiliarSesion.redirigirLoginAdmin();
    }

    Carrera carreraSeleccionada = (Carrera) session.getAttribute("carreraSeleccionada");

    if (carreraSeleccionada == null) {
      return Commands.create(new Command("error", "No hay carrera seleccionada. Fin caso de uso"));
    }

    Caballo caballoSeleccionado = fachadaServicios.buscarCaballoPorNumero(caballoNumero);

    if (caballoSeleccionado == null) {
      return Commands.create(new Command("error", "No se encontro el caballo con numero: " + caballoNumero));
    }

   Participante participanteSeleccionado = carreraSeleccionada.obtenerParticipanteEnCarrera(caballoNumero);

    if (participanteSeleccionado == null) {
      return Commands.create(new Command("error", "El caballo seleccionado no participa en esta carrera."));
    }

    session.setAttribute("caballoSeleccionado", caballoSeleccionado);

    try {
      fachadaServicios.gestionarFinalizarCarrera(carreraSeleccionada, caballoSeleccionado);
    } catch (HipodromoException e) {
      return Commands.create(new Command("error", e.getMessage()));
    }

    return Commands.create(
        new Command("mensaje", "Carrera finalizada correctamente"),
        new Command("mostrarCarreraSeleccionada", new CarreraDto(carreraSeleccionada)),
        new Command("actualizarCaballos", ParticipanteDto.fromCarrera(carreraSeleccionada)),
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
