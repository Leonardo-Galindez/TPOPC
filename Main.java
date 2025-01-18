import java.util.Random;
import Class.Colectivo;
import Class.Parque;
import Class.Visitante;
import controller.ControlColectivo;
import controller.ControlParque;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        int cantPulseras = 20;
        int cantMolinetes = 5;
        int capacidadColectivo = 10;
        int totalVisitantes = 30;

        Parque parque = new Parque(cantPulseras, cantMolinetes);
        Colectivo colectivo = new Colectivo(capacidadColectivo);

        ExecutorService executorService = Executors.newCachedThreadPool();

        // Control del parque
        executorService.execute(new ControlParque(parque, colectivo));
        // Control del colectivo
        executorService.execute(new ControlColectivo(colectivo));

        // Crear visitantes
        ScheduledExecutorService visitorScheduler = Executors.newScheduledThreadPool(1);

        for (int i = 1; i <= totalVisitantes; i++) {
            final int visitanteId = i;
            boolean enColectivo = new Random().nextInt(3) == 0; // 1/3 probabilidad de usar colectivo
            int delay = new Random().nextInt(500); // Tiempo aleatorio de llegada
            visitorScheduler.schedule(() -> {
                executorService.execute(new Visitante(parque, colectivo, visitanteId, enColectivo));
            }, delay, TimeUnit.MILLISECONDS);
        }

        // Esperar a que todos los visitantes terminen
        visitorScheduler.shutdown();
        try {
            if (!visitorScheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("El visitorScheduler no terminó a tiempo.");
            }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar el visitorScheduler.");
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("El executorService no terminó a tiempo.");
            }
        } catch (InterruptedException e) {
            System.out.println("Error al esperar el executorService.");
        }

        System.out.println("Simulación terminada.");
    }
}




