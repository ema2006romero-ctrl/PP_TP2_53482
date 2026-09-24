package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private static int contador = 0;

    public Inscripcion(LocalDate fecha, String estado) {
        this.fecha = fecha;
        this.estado = estado;
    }

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    public boolean estaConfirmada() {
        return "CONFIRMADA".equals(estado);
    }

    public TicketDeAcceso generarTicket() {
        if (!estaConfirmada()) {
            return null;
        }
        return new TicketDeAcceso();
    }

    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }

    public class TicketDeAcceso implements Serializable {
        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso() {
            this.idTicket = "TCK-" + (++contador);
            this.fechaEmision = LocalDate.now();
        }

        public void enviarTicket() {
            System.out.println("[" + Thread.currentThread().getName() + "] Enviando ticket "
                    + idTicket + " (inscripción del " + fecha + ", emitido " + fechaEmision + ")");
        }

        public String getIdTicket() { return idTicket; }
    }
}
