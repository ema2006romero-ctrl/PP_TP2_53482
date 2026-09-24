import Certificacion.Certificable;
import modelo.EventoUniversitario;
import modelo.Sala;
import modelo.Estudiante;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import java.util.List;
import hilos.EnvioTicketsThread;

public class App {
    public static void main(String[] args) {

        Estudiante e1 = new Estudiante("1001", "Santi");
        Estudiante e2 = new Estudiante("1002", "Axel");
        Estudiante e3 = new Estudiante("1003", "Juan");

        EventoUniversitario evento = new EventoUniversitario("EV01", "Semana de la UTN", 5000, false);
        evento.asignarSala(new Sala(1, "Lisun"));

        Taller taller = new Taller(1, "Taller de Java", 10, true);
        Curso curso = new Curso(2, "Curso de IA", 10, 2);
        Charla charla = new Charla(3, "Charla de bases de datos", 50, "Fernandez");

        evento.crearActividad(taller);
        evento.crearActividad(curso);
        evento.crearActividad(charla);

        modelo.Inscripcion i1 = null, i2 = null, i3 = null;
        try {
            i1 = taller.inscribir(e1);
            i2 = curso.inscribir(e2);
            i3 = charla.inscribir(e3);
        } catch (Exception e) {
            System.out.println("Error al inscribir: " + e.getMessage());
        }

        if (i1 != null) i1.confirmar();
        if (i2 != null) i2.confirmar();


        System.out.println("Certificados emitidos");
        for (Actividad actividad : evento.getActividades()) {
            if (actividad instanceof Certificacion.Certificable certificable) {
                for (Estudiante est : actividad.getEstudiantesInscriptos()) {
                    System.out.println(certificable.generarCertificado(est));
                }
            }
        }

        System.out.println("Datos del evento");
        evento.mostrarDatos();

        System.out.println("--- Filtrado por tipo ---");
        List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);

        System.out.println("Charlas: " + charlas.size());
        System.out.println("Talleres: " + talleres.size());
        System.out.println("Cursos: " + cursos.size());

        System.out.println("Costo materiales charlas: " + evento.calcularCostoMateriales(charlas));
        System.out.println("Costo materiales talleres: " + evento.calcularCostoMateriales(talleres));
        System.out.println("Costo materiales cursos: " + evento.calcularCostoMateriales(cursos));

        EnvioTicketsThread envioThread = new EnvioTicketsThread(evento);
        envioThread.start();

        for (int i = 0; i < 3; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Mostrando datos del evento...");
            evento.mostrarDatos();
            for (Actividad actividad : evento.getActividades()) {
                actividad.mostrarInscripciones();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Hilo principal interrumpido: " + e.getMessage());
            }
        }

        try {
            envioThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error esperando el hilo de envío: " + e.getMessage());
        }

        System.out.println("Programa finalizado.");
        }
}
