package ort.da.Obligatorio.dominio;

public class Dividendo {
    private double valor;
    private Participante participante;
    private Carrera carrera;
    // Que comision sea una clase?
    private Comision comision;

    public Dividendo() {
        super();
    }

    //  Dividendo es válido únicamente cuando:
    //  cantidad de apuestas al caballo > 0.
    //  valor es mayor a 1.
    public boolean esValido(){
        throw new UnsupportedOperationException("Unimplemented method 'esValido'");
    }

    // Dividendo = (total apostado en la carrera - comisión) / total apostado al
    // caballo
    public double obtenerValor() {
     double totalApostadoEnCarrera = carrera.getTotalApostado();
        double totalApostadoAlCaballo = participante.getApuestas().stream()
                .filter(apuesta -> apuesta.getCaballo().equals(participante))
                .mapToDouble(Apuesta::getMonto)
                .sum();
        return totalApostadoAlCaballo;
    }
}
