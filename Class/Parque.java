package Class;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Parque {
    private Semaphore pulseras;
    private Semaphore molinetes;
    private Lock lock = new ReentrantLock();
    private Condition parqueAbierto = lock.newCondition();
    private boolean abierto = true;

    public Parque(int cantPulseras, int cantMolinetes) {
        this.pulseras = new Semaphore(cantPulseras);
        this.molinetes = new Semaphore(cantMolinetes);
    }

    public void tomarPulsera(int numVisitante) throws InterruptedException {
        lock.lock();
        try {
            while (!abierto) {
                parqueAbierto.await();
            }
            pulseras.acquire();
            System.out.println("Visitante " + numVisitante + " tomó una pulsera.");
        } finally {
            lock.unlock();
        }
    }

    public void pasarMolinetes(int numVisitante) throws InterruptedException {
        molinetes.acquire();
        try {
            System.out.println("Visitante " + numVisitante + " está pasando por el molinete.");
            Thread.sleep(1000);
        } finally {
            molinetes.release();
        }
    }

    public void salirParque(int numVisitante) {
        pulseras.release();
        System.out.println("Visitante " + numVisitante + " salió del parque.");
    }

    public void abrirParque() {
        lock.lock();
        try {
            abierto = true;
            parqueAbierto.signalAll();
            System.out.println("El parque está abierto.");
        } finally {
            lock.unlock();
        }
    }

    public void cerrarParque() {
        lock.lock();
        try {
            abierto = false;
            System.out.println("El parque está cerrado.");
        } finally {
            lock.unlock();
        }
    }
}
