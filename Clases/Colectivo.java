package Clases;

import java.util.concurrent.Semaphore;

public class Colectivo implements Runnable {

    private Semaphore asientos; // Semáforo para los asientos del colectivo
    private final int capacidad;
    private final int tiempoEspera;
    private int visitantesEnColectivo = 0; // Contador de visitantes en el colectivo
    private boolean enRecorrido = false;

    public Colectivo(int capacidad, int tiempoEspera) {
        this.capacidad = capacidad;
        this.tiempoEspera = tiempoEspera;
        this.asientos = new Semaphore(capacidad);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // Simula espera de visitantes
                iniciarRecorridoIda();
                Thread.sleep(2000); // Simula el trayecto hacia el parque
                finalizarRecorridoIda();

                // Espera hasta que todos los visitantes bajen
                esperarDescensoVisitantes();

                iniciarRecorridoVuelta();
                Thread.sleep(2000); // Simula el trayecto hacia el estacionamiento
                finalizarRecorridoVuelta();

            } catch (InterruptedException e) {
                System.out.println("El colectivo se ha detenido.");
                break;
            }
        }
    }

    public synchronized void subirColectivo(int numVisitante) throws InterruptedException {
        while (enRecorrido || asientos.availablePermits() == 0) {
            System.out.println("El visitante " + numVisitante + " está esperando para subir al colectivo.");
            wait(); // Bloquea el hilo hasta que el colectivo esté listo
        }
        asientos.acquire();
        visitantesEnColectivo++;
        System.out.println("El visitante " + numVisitante + " ha subido al colectivo.");
        notifyAll(); // Notifica al colectivo que puede iniciar si hay al menos un visitante
    }

    public synchronized void bajarColectivo(int numVisitante) throws InterruptedException {
        while (enRecorrido) {
            wait(); // Espera a que el colectivo termine el recorrido
        }
        asientos.release();
        visitantesEnColectivo--;
        System.out.println("El visitante " + numVisitante + " ha bajado del colectivo.");
        notifyAll(); // Notifica si quedan hilos en espera
    }

    private synchronized void esperarDescensoVisitantes() throws InterruptedException {
        while (visitantesEnColectivo > 0) {
            wait(); // Espera a que todos los visitantes bajen
        }
    }

    private synchronized void iniciarRecorridoIda() throws InterruptedException {
        while (visitantesEnColectivo == 0) {
            System.out.println("El colectivo está esperando que al menos un visitante suba.");
            wait(); // Espera hasta que al menos un visitante suba
        }
        enRecorrido = true;
        System.out.println("El colectivo está iniciando el recorrido hacia el parque.");
    }

    private synchronized void finalizarRecorridoIda() {
        enRecorrido = false;
        System.out.println("El colectivo ha llegado al parque.");
        notifyAll(); // Notifica a los visitantes que pueden bajar
    }

    private synchronized void iniciarRecorridoVuelta() {
        enRecorrido = true;
        System.out.println("El colectivo está iniciando el recorrido de vuelta al estacionamiento.");
    }

    private synchronized void finalizarRecorridoVuelta() {
        enRecorrido = false;
        System.out.println("El colectivo ha llegado al estacionamiento.");
        liberarAsientos();
        notifyAll(); // Notifica a los visitantes que pueden subir
    }

    private void liberarAsientos() {
        asientos = new Semaphore(capacidad); // Restablece los permisos para el siguiente viaje
    }
}
