package hilos;

import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        for (Actividad actividad : evento.getActividades()) {
            for (Inscripcion inscripcion : actividad.getInscripciones()) {
                Inscripcion.TicketDeAcceso ticket = inscripcion.generarTicket();
                if (ticket != null) {
                    ticket.enviarTicket();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println("Envío interrumpido: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Finalizó el envío de todos los tickets.");
    }
}
