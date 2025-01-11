package Class;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Colectivo {
    private Semaphore asientos;
    private Lock lock = new ReentrantLock();
    private Condition puedeSubir = lock.newCondition();
    private Condition puedeBajar = lock.newCondition();
    private int visitantesEnColectivo = 0;
    private boolean enRecorrido = false;
    private boolean parqueCerrado = false;

    public Colectivo(int capacidad) {
        this.asientos = new Semaphore(capacidad);
    }

    public void marcarParqueCerrado() {
        lock.lock();
        try {
            parqueCerrado = true;
        } finally {
            lock.unlock();
        }
    }

    public void subirColectivo(int numVisitante) throws InterruptedException {
        lock.lock();
        try {
            while (enRecorrido || asientos.availablePermits() == 0) {
                puedeSubir.await();
            }
            asientos.acquire();
            visitantesEnColectivo++;
            System.out.println("Visitante " + numVisitante + " subió al colectivo.");
        } finally {
            lock.unlock();
        }
    }

    public void bajarColectivo(int numVisitante) throws InterruptedException {
        lock.lock();
        try {
            while (!enRecorrido) {
                puedeBajar.await();
            }
            visitantesEnColectivo--;
            asientos.release();
            System.out.println("Visitante " + numVisitante + " bajó del colectivo.");
            if (visitantesEnColectivo == 0) {
                puedeBajar.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void iniciarRecorrido() throws InterruptedException {
        lock.lock();
        try {
            while (!parqueCerrado && visitantesEnColectivo == 0) {
                puedeSubir.await();
            }
            enRecorrido = true;
            System.out.println("El colectivo está en recorrido.");
        } finally {
            lock.unlock();
        }
    }

    public void finalizarRecorrido() {
        lock.lock();
        try {
            enRecorrido = false;
            System.out.println("El colectivo llegó a destino.");
            puedeBajar.signalAll();
            puedeSubir.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
