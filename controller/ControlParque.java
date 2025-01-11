package controller;

import Class.Colectivo;
import Class.Parque;
public class ControlParque implements Runnable {
    private Parque parque;
    private Colectivo colectivo;

    public ControlParque(Parque parque, Colectivo colectivo) {
        this.parque = parque;
        this.colectivo = colectivo;
    }

    @Override
    public void run() {
        try {
            parque.abrirParque();
            Thread.sleep(20000); // El parque permanece abierto por 20 segundos
            parque.cerrarParque();
            colectivo.marcarParqueCerrado();
        } catch (InterruptedException e) {
            System.out.println("Error en el control del parque.");
        }
    }
}


