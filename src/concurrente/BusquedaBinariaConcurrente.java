package concurrente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BusquedaBinariaConcurrente {
    public static int busquedaBinaria(int[] vector, int valorBuscado) throws InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] hilos = new Thread[numThreads];
        AtomicInteger resultado = new AtomicInteger(-1);
        AtomicBoolean encontrado = new AtomicBoolean(false);

        int chunkSize = (vector.length + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(vector.length, start + chunkSize);

            hilos[i] = new Thread(() -> {
                if (encontrado.get()) return;

                int index = Arrays.binarySearch(vector, start, end, valorBuscado);
                if (index >= 0 && encontrado.compareAndSet(false, true)) {
                    resultado.set(index); // Ajuste al índice global
                }
            });

            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        return resultado.get();
    }

}
