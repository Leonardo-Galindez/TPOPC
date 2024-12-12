package Clases;

import java.util.concurrent.*;

public class Parque {

    private int cantPulseras = 25;
    private int cantMolinetes = 5;
    private Semaphore pulseras = new Semaphore(cantPulseras);
    private Semaphore molinetes = new Semaphore(cantMolinetes);
    // true -> abierto, false -> cerrado
    private boolean estadoParque =true;

    // Parque
    public void abrirParque() {
        estadoParque = true;
        System.out.println("El parque esta abierto");
    }

    public void cerrarParque() {
        estadoParque = false;
        System.out.println("El parque esta cerrado");
    }

    public void simulacionEstadoParque() {
        try {
            Thread.sleep(60000);
            cerrarParque();
            Thread.sleep(60000);
            abrirParque();
        } catch (InterruptedException ex) {
            System.out.println("Error al simular el estado del parque");
        }
    }

    // Visitante
    public void tomarPulsera(int numVistante) {
        try {
            if (estadoParque) {
                System.out.println("El visitante " + numVistante + " esta esperando para tomar un pulsera");
                pulseras.acquire();
                System.out.println("El visintante " + numVistante + " tomo una pulsera");
                // simulación
                Thread.sleep(1000);
            } else {
                System.out.println("El visitante " + numVistante + " no pudo ingresar al parque porque esta cerrado");
            }

        } catch (InterruptedException ex) {
            System.out.println("Error al ingresar al parque");
        }
    }

    public void pasarMolinetes(int numVistante) {
        try {
            System.out.println("El visitante " + numVistante + " esta esperando para pasar los molinetes");
            molinetes.acquire();
            System.out.println("El visitante " + numVistante + " paso por los molinetes");
            // simulación
            Thread.sleep(1000);
            molinetes.release();
            System.out.println("El visitante " + numVistante + " Ingreso al parque");
        } catch (InterruptedException ex) {
            System.out.println("Error al ingresar al parque");
        }
    }

    public void salirDelParque(int numVistante) {
        System.out.println("El visitante " + numVistante + " salio del parque");
        pulseras.release();
    }

    // Visitante Bus
    public void ingresarEstacionamiento() {

    }

}
