package Clases;

import java.util.Random;

public class Visitante implements Runnable {

    private Parque parque;
    private Colectivo colectivo;
    private final int numVisitante;
    private final boolean vaEnColectivo;

    public Visitante(Parque parque, Colectivo colectivo, int numVisitante) {
        this.parque = parque;
        this.colectivo = colectivo;
        this.numVisitante = numVisitante;
        this.vaEnColectivo = new Random().nextBoolean(); // Decide aleatoriamente si va en colectivo
    }

    @Override
    public void run() {
        try {
            if (vaEnColectivo) {
                colectivo.subirColectivo(numVisitante);
                simulacionViajeColectivo();
                colectivo.bajarColectivo(numVisitante);
            } else {
                System.out.println("El visitante " + numVisitante + " fue caminando al parque.");
            }
            parque.tomarPulsera(numVisitante);
            simulacionMolinetes();
            parque.pasarMolinetes(numVisitante);
            simulacionVisita();
            parque.salirDelParque(numVisitante);
        } catch (Exception e) {
            System.out.println("Error al ingresar al parque.");
        }
    }

    public void simulacionVisita() throws InterruptedException {
        System.out.println("El visitante " + numVisitante + " está disfrutando del parque.");
        Thread.sleep(2000);
    }

    public void simulacionMolinetes() throws InterruptedException {
        System.out.println("El visitante " + numVisitante + " va a los molinetes.");
        Thread.sleep(1000);
    }

    public void simulacionViajeColectivo() throws InterruptedException {
        System.out.println("El visitante " + numVisitante + " está en el colectivo.");
        Thread.sleep(1000);
    }
}
