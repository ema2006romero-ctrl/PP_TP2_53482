package modelo;

import modelo.actividades.Actividad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades = new ArrayList<>();

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this(otro.id, otro.titulo, otro.costoBase, otro.gratuito);
    }

    public double calcularCostoEstimado() {
        if (gratuito == true) {
            return 0;
        } else return costoBase;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    public void mostrarDatos() {
        System.out.println("Evento: " + titulo + " (id=" + id + ")");
        System.out.println("Costo estimado: " + calcularCostoEstimado());
        if (sala != null) System.out.println("Sala: " + sala.getNombre());
        for (Actividad a : actividades) {
            a.mostrarIdentificacion();
        }
    }

    public boolean persistirEvento() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(id + ".dat"))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar el evento: " + e.getMessage());
            return false;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(id + ".dat"))) {
            return (EventoUniversitario) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existe un archivo guardado con id " + id);
        } catch (IOException e) {
            System.out.println("Error de lectura del archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró la clase del objeto guardado: " + e.getMessage());
        }
        return null;
    }

    public static int getCantidadEventos() { return cantidadEventos; }
    public String getId() { return id; }
    public String getTitulo() { return titulo; }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad actividad : actividades) {
            if (tipo.isInstance(actividad)) {
                resultado.add(tipo.cast(actividad));
            }
        }
        return resultado;
    }

    public double calcularCostoMateriales(List<? extends Actividad> actividadesLista) {
        double total = 0;
        for (Actividad actividad : actividadesLista) {
            total += actividad.calcularCostoMateriales();
        }
        return total;
    }
}
