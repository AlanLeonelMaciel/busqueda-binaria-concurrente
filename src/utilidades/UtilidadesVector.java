package utilidades;

import java.util.Random;

public class UtilidadesVector {

    public static void generarVectorOrdenado(int tam, int[] vector) {
        for (int i = 0; i < tam; i++) {
            vector[i] = i+1;
        }
    }

    public static void mostrarVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }
}
