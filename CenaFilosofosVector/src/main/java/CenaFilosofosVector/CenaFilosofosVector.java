package CenaFilosofosVector;

import java.util.concurrent.locks.ReentrantLock;

public class CenaFilosofosVector {
    static class Filosofo implements Runnable {
        private final int id;
        private final int valor;  // Valor del vector que este filósofo multiplicará
        private final int escalar; // Escalar con el que se multiplicará
        private final int[] resultado; // Arreglo para almacenar el resultado
        private final ReentrantLock lock; // Lock para sincronizar el acceso

        public Filosofo(int id, int valor, int escalar, int[] resultado, ReentrantLock lock) {
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
            System.out.println("Filósofo " + id + " está pensando.");
            try {
                Thread.sleep((int) (Math.random() * 500)); // Simula el tiempo de pensar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private int multiplicar(int valor, int escalar) {
            System.out.println("Filósofo " + id + " está multiplicando " + valor + " * " + escalar);
            return valor * escalar;
        }

        private void escribirResultado(int indice, int valor) {
            lock.lock(); // Bloqueo para escribir el resultado
            try {
                resultado[indice] = valor;
                System.out.println("Filósofo " + id + " escribió el resultado: " + valor);
            } finally {
                lock.unlock(); // Liberar el bloqueo
            }
        }
    }

    public static void main(String[] args) {
        int escalar = 4; // Escalar para la multiplicación
        int[] vector = {1, 2, 3}; // Vector de entrada
        int[] resultado = new int[vector.length]; // Vector para almacenar el resultado

        ReentrantLock lock = new ReentrantLock(); // Lock para sincronizar el acceso
        Thread[] hilos = new Thread[vector.length];

        // Crear filósofos (hilos) para multiplicar
        for (int i = 0; i < vector.length; i++) {
            hilos[i] = new Thread(new Filosofo(i, vector[i], escalar, resultado, lock));
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
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
