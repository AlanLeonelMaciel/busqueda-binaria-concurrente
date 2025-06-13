package test;

import concurrente.BusquedaBinariaConcurrente;
import utilidades.UtilidadesVector;

import java.util.Arrays;


public class Testeo {
    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int[] tamanios = {10_000, 100_000, 1_000_000, 10_000_000, 50_000_000, 100_000_000};

        System.out.printf("%-15s %-20s %-20s %-20s %-20s%n",
                "Tamaño", "Índice Secuencial", "Índice Concurrente", "Tiempo Sec. (ns)", "Tiempo Conc. (ns)");

        for (int TAM : tamanios) {
            int[] vector = new int[TAM];
            UtilidadesVector.generarVectorOrdenado(TAM, vector);

            int valorBuscado = TAM ; // último valor para forzar búsqueda completa

            int indiceSecuencial = -1;
            int indiceConcurrente = -1;
            long tiempoSecuencial = -1;
            long tiempoConcurrente = -1;

            // Medir tiempo concurrente
            try {
                long inicioConcurrente = System.nanoTime();
                indiceConcurrente = BusquedaBinariaConcurrente.busquedaBinaria(vector, valorBuscado);
                long finConcurrente = System.nanoTime();
                tiempoConcurrente = (finConcurrente - inicioConcurrente);
            } catch (InterruptedException e) {
                System.out.println("Error concurrente: " + e);
            }

            // Medir tiempo secuencial
            long inicioSecuencial = System.nanoTime();
            indiceSecuencial = Arrays.binarySearch(vector, valorBuscado);
            long finSecuencial = System.nanoTime();
            tiempoSecuencial = (finSecuencial - inicioSecuencial);

            // Mostrar resultados en tabla
            System.out.printf("%-15d %-20d %-20d %-20d %-20d%n",
                    TAM, indiceSecuencial, indiceConcurrente, tiempoSecuencial, tiempoConcurrente);
        }
    }
}
