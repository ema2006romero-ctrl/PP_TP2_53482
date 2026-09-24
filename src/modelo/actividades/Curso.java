package modelo.actividades;

import Certificacion.Certificable;
import modelo.Estudiante;


public class Curso extends Actividad implements Certificable{
    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel){
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 200;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado de asistencia al " + getTipo() + " '" + getTitulo() +
                "' otorgado a " + estudiante.getNombre() + " - Emitido por " + Certificable.ENTIDAD_EMISORA;
    }
}
