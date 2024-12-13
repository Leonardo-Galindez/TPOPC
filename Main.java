import Clases.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Parque parque = new Parque(25, 5, true);
        Colectivo colectivo = new Colectivo(25, 5);

        Thread estadoParque = new Thread(parque);
        Thread hiloColectivo = new Thread(colectivo);

        estadoParque.start();
        hiloColectivo.start();

        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            Thread visitante = new Thread(new Visitante(parque, colectivo, i));
            visitante.start();
            try {
                Thread.sleep(200 + random.nextInt(800)); // Llegadas aleatorias
            } catch (InterruptedException e) {
                System.out.println("Error al simular llegada de visitantes.");
            }
        }
    }
}
