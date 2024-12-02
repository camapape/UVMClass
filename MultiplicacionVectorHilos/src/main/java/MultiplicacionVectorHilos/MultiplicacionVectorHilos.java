package MultiplicacionVectorHilos;

import java.util.concurrent.locks.ReentrantLock;

public class MultiplicacionVectorHilos {
    static class Trabajador implements Runnable {
        private final int id;
        private final int valor;
        private final int escalar;
        private final int[] resultado;
        private final ReentrantLock lock;

        public Trabajador(int id, int valor, int escalar, int[] resultado, ReentrantLock lock) {
            this.id = id;
            this.valor = valor;
            this.escalar = escalar;
            this.resultado = resultado;
            this.lock = lock;
        }

        @Override
        public void run() {
            pensar();
            int res = multiplicar(valor, escalar);
            escribirResultado(id, res);
        }

        private void pensar() {
            System.out.println("Hilo " + id + " está pensando.");
            try {
                Thread.sleep((int) (Math.random() * 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private int multiplicar(int valor, int escalar) {
            System.out.println("Hilo " + id + " está multiplicando " + valor + " * " + escalar);
            return valor * escalar;
        }

        private void escribirResultado(int indice, int valor) {
            lock.lock(); // Bloqueo para escribir en el vector de resultados
            try {
                resultado[indice] = valor;
                System.out.println("Hilo " + id + " escribió el resultado: " + valor);
            } finally {
                lock.unlock(); // Liberar el bloqueo
            }
        }
    }

    public static void main(String[] args) {
        int escalar = 4; // Escalar para la multiplicación
        int[] vector = {1, 2, 3, 4, 5}; // Vector de entrada
        int[] resultado = new int[vector.length]; // Vector para almacenar el resultado

        ReentrantLock lock = new ReentrantLock(); // Lock para sincronización
        Thread[] hilos = new Thread[vector.length];

        // Crear hilos para la multiplicación
        for (int i = 0; i < vector.length; i++) {
            hilos[i] = new Thread(new Trabajador(i, vector[i], escalar, resultado, lock));
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Mostrar el resultado
        System.out.print("Resultado de la multiplicación: ");
        for (int valor : resultado) {
            System.out.print(valor + " ");
        }
    }
}
