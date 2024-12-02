package filosofoscenando;

import java.util.concurrent.locks.ReentrantLock;

public class FilosofosCenando {
    // Clase que representa un tenedor
    static class Tenedor {
        private final ReentrantLock lock = new ReentrantLock();

        public boolean tomar() {
            return lock.tryLock(); // Intenta tomar el tenedor
        }

        public void soltar() {
            lock.unlock(); // Suelta el tenedor
        }
    }

    // Clase que representa un filósofo
    static class Filosofo implements Runnable {
        private final int id;
        private final Tenedor tenedorIzquierdo;
        private final Tenedor tenedorDerecho;

        public Filosofo(int id, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho) {
            this.id = id;
            this.tenedorIzquierdo = tenedorIzquierdo;
            this.tenedorDerecho = tenedorDerecho;
        }

        @Override
        public void run() {
            while (true) {
                pensar();
                if (intentarComer()) {
                    comer();
                    soltarTenedores();
                }
            }
        }

        private void pensar() {
            System.out.println("Filósofo " + id + " está pensando.");
            try {
                Thread.sleep((int) (Math.random() * 1000)); // Simula el tiempo de pensar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private boolean intentarComer() {
            // Tomar los tenedores en orden para evitar interbloqueo
            if (tenedorIzquierdo.tomar()) {
                if (tenedorDerecho.tomar()) {
                    return true; // Ambos tenedores fueron tomados exitosamente
                } else {
                    tenedorIzquierdo.soltar(); // Soltar si no puede tomar el derecho
                }
            }
            return false;
        }

        private void comer() {
            System.out.println("Filósofo " + id + " está comiendo.");
            try {
                Thread.sleep((int) (Math.random() * 1000)); // Simula el tiempo de comer
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void soltarTenedores() {
            tenedorIzquierdo.soltar();
            tenedorDerecho.soltar();
            System.out.println("Filósofo " + id + " ha soltado los tenedores.");
        }
    }

    // Método principal
    public static void main(String[] args) {
        final int NUM_FILOSOFOS = 5;
        Tenedor[] tenedores = new Tenedor[NUM_FILOSOFOS];
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
        Thread[] hilos = new Thread[NUM_FILOSOFOS];

        // Inicializar los tenedores
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            tenedores[i] = new Tenedor();
        }

        // Crear filósofos y asignar tenedores
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            Tenedor tenedorIzquierdo = tenedores[i];
            Tenedor tenedorDerecho = tenedores[(i + 1) % NUM_FILOSOFOS];
            filosofos[i] = new Filosofo(i, tenedorIzquierdo, tenedorDerecho);
            hilos[i] = new Thread(filosofos[i], "Filósofo " + i);
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }
    }
}
