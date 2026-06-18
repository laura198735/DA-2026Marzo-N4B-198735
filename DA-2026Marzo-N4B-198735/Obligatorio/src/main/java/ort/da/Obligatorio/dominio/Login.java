package ort.da.Obligatorio.dominio;


import java.util.Date;

import lombok.Getter;

public class Login {
    @Getter
    Date fechaHoraIngreso;
    @Getter
    Usuario usuario;

    public Login(Date fechaHoraIngreso, Usuario usuario) {
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.usuario = usuario;
    }
}
