package modelo.actividades;

import modelo.Estudiante;
import modelo.Inscripcion;
import Excepciones.CupoExcedidoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Actividad implements Serializable{
    public static final int CupoMinimo = 5;

    private int id;
    private String titulo;
    private int CupoMaximo;
    private List<Inscripcion> Inscripciones = new ArrayList<>();
    private List<Estudiante> estudiantesInscriptos = new ArrayList<>();


    public Actividad(int id, String titulo, int CupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.CupoMaximo = CupoMaximo;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (Inscripciones.size() >= CupoMaximo) {
            throw new CupoExcedidoException(
                    "No se puede inscribir a " + estudiante.getNombre() +
                            ": la actividad '" + titulo + "' alcanzó su cupo máximo "
            );
        }
        Inscripcion inscripcion = new Inscripcion(LocalDate.now(), "PENDIENTE");
        Inscripciones.add(inscripcion);
        estudiantesInscriptos.add(estudiante);
        return inscripcion;
    }

    public List<Estudiante> getEstudiantesInscriptos() {
        return estudiantesInscriptos;
    }

    public void mostrarInscripciones() {
        System.out.println("Actividad: " + titulo + " (" + Inscripciones.size() + " inscriptos)");
    }

    public final void mostrarIdentificacion() {
        System.out.println("ID: " + id + " - Título: " + titulo);
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public List<Inscripcion> getInscripciones() {
        return Inscripciones;
    }
}


