import java.util.LinkedList;
import java.util.Queue;

public class ColaImpresion {

    private final Queue<String> colaImpresion = new LinkedList<>();
    private final int capacidadMaxima;

    public ColaImpresion(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    // Método para añadir un documento a la cola
    public synchronized void agregarDocumento(String documento) throws InterruptedException {
        while (colaImpresion.size() == capacidadMaxima) {
            System.out.println("La cola de impresión está llena. Esperando...");
            wait(); // Esperar si la cola está llena
        }
        colaImpresion.add(documento);
        System.out.println("Documento agregado: " + documento);
        notifyAll(); // Notificar que hay documentos en la cola
    }

    // Método para imprimir un documento
    public synchronized void imprimirDocumento() throws InterruptedException {
        while (colaImpresion.isEmpty()) {
            System.out.println("La cola de impresión está vacía. Esperando...");
            wait(); // Esperar si no hay documentos
        }
        String documento = colaImpresion.poll();
        System.out.println("Imprimiendo documento: " + documento);
        Thread.sleep(2000); // Simular el tiempo de impresión
        System.out.println("Documento impreso: " + documento);
        notifyAll(); // Notificar que hay espacio en la cola
    }

    public static void main(String[] args) {
        ColaImpresion colaImpresion = new ColaImpresion(5); // Capacidad máxima de la cola

        // Hilo productor: Agregar documentos a la cola
        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    colaImpresion.agregarDocumento("Documento " + i);
                    Thread.sleep(500); // Simular el tiempo de generación de documentos
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Hilo consumidor: Imprimir documentos de la cola
        Thread consumidor = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    colaImpresion.imprimirDocumento();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Proceso de impresión finalizado.");
    }
}
