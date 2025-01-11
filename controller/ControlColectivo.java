package controller;

import Class.Colectivo;
import Class.Parque;

public class ControlColectivo implements Runnable {
    private Colectivo colectivo;

    public ControlColectivo(Colectivo colectivo) {
        this.colectivo = colectivo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // El colectivo espera que haya visitantes para iniciar el recorrido
                Thread.sleep(5000); // Simulación del tiempo que pasa antes de un nuevo recorrido
                colectivo.iniciarRecorrido();
                Thread.sleep(2000); // Tiempo de recorrido del colectivo
                colectivo.finalizarRecorrido();
            } catch (InterruptedException e) {
                System.out.println("Error en el control del colectivo.");
                break;
            }
        }
    }
}
