package Class;

import java.util.Random;

public class Visitante implements Runnable {
    private Parque parque;
    private Colectivo colectivo;
    private int numVisitante;
    private boolean enColectivo;

    public Visitante(Parque parque, Colectivo colectivo, int numVisitante, boolean enColectivo) {
        this.parque = parque;
        this.colectivo = colectivo;
        this.numVisitante = numVisitante;
        this.enColectivo = enColectivo;
    }

    @Override
    public void run() {
        try {
            // Fase 1: Llegada al parque
            if (enColectivo) {
                colectivo.subirColectivo(numVisitante);
                colectivo.iniciarRecorrido();
                Thread.sleep(2000); // Simulación del recorrido
                colectivo.finalizarRecorrido();
                colectivo.bajarColectivo(numVisitante);
            } else {
                System.out.println("Visitante " + numVisitante + " llegó al parque por su cuenta.");
            }

            // Fase 2: Entrada al parque
            Thread.sleep(new Random().nextInt(500)); // Tiempo aleatorio antes de tomar la pulsera
            parque.tomarPulsera(numVisitante);

            // Fase 3: Pasar por el molinete
            parque.pasarMolinetes(numVisitante);

            // Fase 4: Permanecer en el parque
            Thread.sleep(1000 + new Random().nextInt(3000)); // Simulación del tiempo en el parque

            // Fase 5: Salida del parque
            parque.salirParque(numVisitante);
        } catch (InterruptedException e) {
            System.out.println("Visitante " + numVisitante + " tuvo un problema.");
        }
    }
}

