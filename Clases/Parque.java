package Clases;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Parque implements Runnable {

    private Semaphore pulseras;
    private Semaphore molinetes;
    private Semaphore colectivo;
    private boolean estadoParque; // true = abierto, false = cerrado
    private final ReentrantLock lock = new ReentrantLock(); // Lock para sincronizar el estado del parque
    private final Condition parqueAbierto = lock.newCondition(); // Condición para esperar a que el parque abra

    public Parque(int cantPulseras, int cantMolinetes, boolean estadoParque) {
        this.estadoParque = estadoParque;
        this.pulseras = new Semaphore(cantPulseras);
        this.molinetes = new Semaphore(cantMolinetes);
        this.colectivo = new Semaphore(25);
    }

    // Parque
    public void abrirParque() {
        lock.lock();
        try {
            estadoParque = true;
            System.out.println("El parque está abierto");
            parqueAbierto.signalAll(); // Notificar a los visitantes en espera
        } finally {
            lock.unlock();
        }
    }

    public void cerrarParque() {
        lock.lock();
        try {
            estadoParque = false;
            System.out.println("El parque está cerrado");
        } finally {
            lock.unlock();
        }
    }

    public boolean estadoParque() {
        lock.lock();
        try {
            return estadoParque;
        } finally {
            lock.unlock();
        }
    }

    // Simulación del estado del parque
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // Abierto por 2 segundos
                cerrarParque();
                Thread.sleep(2000); // Cerrado por 2 segundos
                abrirParque();
            } catch (InterruptedException ex) {
                System.out.println("Error al simular el estado del parque");
                break;
            }
        }
    }

    public void subirColectivo(){
        try {
            
            colectivo.acquire();
            
        } catch (InterruptedException ex) {
            System.out.println("Error al subir al colectivo");
        }
    }

    // Visitante
    public void tomarPulsera(int numVisitante) {
        lock.lock();
        try {
            // Esperar hasta que el parque esté abierto
            while (!estadoParque) {
                System.out.println("El visitante " + numVisitante + " está esperando a que el parque abra");
                parqueAbierto.await();
            }
            System.out.println("El visitante " + numVisitante + " está esperando para tomar una pulsera");
            pulseras.acquire();
            System.out.println("El visitante " + numVisitante + " tomó una pulsera");
        } catch (InterruptedException ex) {
            System.out.println("Error al ingresar al parque");
        } finally {
            lock.unlock();
        }
    }

    public void pasarMolinetes(int numVisitante) {
        try {
            System.out.println("El visitante " + numVisitante + " está esperando para pasar los molinetes");
            molinetes.acquire();
            System.out.println("El visitante " + numVisitante + " pasó por los molinetes");
            // Simulación
            Thread.sleep(500);
            molinetes.release();
            System.out.println("El visitante " + numVisitante + " ingresó al parque");
        } catch (InterruptedException ex) {
            System.out.println("Error al ingresar al parque");
        }
    }

    public void salirDelParque(int numVisitante) {
        System.out.println("El visitante " + numVisitante + " salió del parque");
        pulseras.release();
    }
}
