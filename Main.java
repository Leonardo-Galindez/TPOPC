import Clases.Parque;
import Clases.Visitante;

public class Main {
    public static void main(String[] args) {
        Parque parque = new Parque();

        for (int i = 0; i < 30; i++) {
            Thread visitante = new Thread(new Visitante(parque, i));
            visitante.start();
        }
    }
}