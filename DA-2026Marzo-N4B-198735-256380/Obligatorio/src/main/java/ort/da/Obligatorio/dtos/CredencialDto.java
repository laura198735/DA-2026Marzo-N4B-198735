package ort.da.Obligatorio.dtos;

import ort.da.Obligatorio.dominio.Credencial;

import java.util.List;

import lombok.Data;

@Data
public class CredencialDto {
    private String nombre;
    private String password;

    public CredencialDto() {
    }

    public CredencialDto(Credencial credencial) {
        this.nombre = credencial.getNombre();
        this.password = credencial.getPassword();
    }

    public Credencial toCredencial() {
        Credencial credencial = new Credencial();
        credencial.setNombre(this.nombre);
        credencial.setPassword(this.password);
        return credencial;
    }

    // convierte una lista de Credencial a una lista de CredencialDto
    public static List<CredencialDto> fromList(List<Credencial> credenciales) {
        return credenciales.stream()
                .map(CredencialDto::new)
                .toList();
    }
}
