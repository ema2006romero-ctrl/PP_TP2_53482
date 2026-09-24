package modelo.actividades;

import Certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook) {
            return 500.0;
        } else {
            return 100.0;
        }
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado de asistencia al " + getTipo() + " '" + getTitulo() +
                "' otorgado a " + estudiante.getNombre() + " - Emitido por " + ENTIDAD_EMISORA;
    }
}
