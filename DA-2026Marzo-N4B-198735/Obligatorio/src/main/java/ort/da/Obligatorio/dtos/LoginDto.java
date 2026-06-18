package ort.da.Obligatorio.dtos;

import java.util.Date;
import java.util.List;

import lombok.Data;
import ort.da.Obligatorio.dominio.Login;

@Data
public class LoginDto {
    String nombreUsuario;
    int cantidadContactos;
    Date fechaHoraIngreso;

    public static List<LoginDto> fromList(List<Login> logines) {
        return logines.stream().map(login -> {
            LoginDto loginDto = new LoginDto();
            loginDto.setNombreUsuario(login.getUsuario().getNombreUsuario());
            loginDto.setFechaHoraIngreso(login.getFechaHoraIngreso());
            return loginDto;
        }).toList();
    }
}
