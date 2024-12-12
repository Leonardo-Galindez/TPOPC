package Clases;

import java.util.Random;

public class Visitante implements Runnable {
    private final Parque parque;
    private final int numVisitante;

    public Visitante(Parque parque, int numVisitante) {
        this.parque = parque;
        this.numVisitante = numVisitante;
    }

    @Override
    public void run() {
        try {
            parque.tomarPulsera(numVisitante);
            parque.pasarMolinetes(numVisitante);
            simulacionVisita();
            parque.salirDelParque(numVisitante);
        } catch (Exception e) {
            System.out.println("Error al ingresar al parque");
        }
    }

    public void simulacionVisita() {
        try {
            System.out.println("El visitante " + numVisitante + " esta disfrutando del parque");
            Thread.sleep((new Random()).nextInt(1000) + 1000);
        } catch (InterruptedException e) {
            System.out.println("Error al simular la visita");
        }
    }

}
