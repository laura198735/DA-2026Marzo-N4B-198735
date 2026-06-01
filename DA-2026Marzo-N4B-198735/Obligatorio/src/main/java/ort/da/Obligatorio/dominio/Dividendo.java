package ort.da.Obligatorio.dominio;

public class Dividendo {
    private double valor;
    private Participante participante;
    private Carrera carrera;

    public Dividendo() {
        super();
    }

    // Dividendo es válido únicamente cuando:
    // - cantidad de apuestas al caballo > 0.
    // - valor es mayor a 1.
    public boolean esValido() {
        if (participante.getApuestas() == null || participante.getApuestas().isEmpty()) {
            return false; // No hay apuestas al caballo
        }
        return valor > 1;
    }

    // Dividendo = (total apostado en la carrera - comisión) / total apostado al
    // caballo
    public double obtenerValor() {
        double totalApostadoAlCaballo = participante.getApuestas().stream()
                .filter(apuesta -> apuesta.getCaballo().equals(participante))
                .mapToDouble(Apuesta::getMonto)// suma el monto de cada apuesta al caballo
                .sum();
        // Nota: cálculo de comisión y valor final quedó incompleto en el original.
        // Por ahora devolvemos el factor por el cual se multiplicará la apuesta.
        return valor;
    }

    // Getter usado por llamadas a registro.getDividendo().getValor()
    public double getValor() {
        return valor;
    }
}
