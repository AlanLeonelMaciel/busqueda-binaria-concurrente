package concurrente;

import java.util.Arrays;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BusquedaBinariaConcurrente {
    public static int busquedaBinaria(int[] vector, int valorBuscado) throws InterruptedException {
        // Obtiene el número de núcleos disponibles para crear igual cantidad de hilos
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] hilos = new Thread[numThreads];
        // Variable atómica para guardar el resultado (índice encontrado)
        AtomicInteger resultado = new AtomicInteger(-1);
        // Bandera atómica para indicar si ya se encontró el valor
        AtomicBoolean encontrado = new AtomicBoolean(false);

        // Calcula el tamaño de cada segmento que procesará cada hilo
        int chunkSize = (vector.length + numThreads - 1) / numThreads;

        // Crea y lanza los hilos
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize; // Índice inicial del segmento
            int end = Math.min(vector.length, start + chunkSize); // Índice final (no inclusivo)

            hilos[i] = new Thread(() -> {
                // Si ya se encontró el valor, el hilo termina
                if (encontrado.get()) return;

                // Realiza búsqueda binaria en el segmento asignado
                int index = Arrays.binarySearch(vector, start, end, valorBuscado);
                // Si lo encuentra y es el primero en hacerlo, guarda el resultado
                if (index >= 0 && encontrado.compareAndSet(false, true)) {
                    resultado.set(index);
                }
            });

            hilos[i].start(); // Inicia el hilo
        }

        // Espera a que todos los hilos terminen
        for (Thread hilo : hilos) {
            hilo.join();
        }

        // Devuelve el índice encontrado o -1 si no se encontró
        return resultado.get();
    }
}
