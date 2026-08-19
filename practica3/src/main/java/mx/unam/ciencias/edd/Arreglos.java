package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Intercambia de posicion los dos elementos del arreglo.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a usar.
     * @param i el i-esimo elemento del arreglo.
     * @param j el j-esimo elemento del arreglo.
     */
    private static <T> void swap(T[] arreglo, int i, int j) {
        T aux = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = aux;
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, comparador, 0, arreglo.length - 1);
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando QuickSort implementado de manera recursiva.
     * @param <T> el tipo del que puede ser el arreglo.
     * @param arreglo el (sub)arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     * @param bajo el limite inferior del (sub)arreglo a ordenar.
     * @param alto el limite superior del (sub)arreglo a ordenar.
     */
    public static <T> void 
    quickSort(T[] arreglo, Comparator<T> comparador, int bajo, int alto) {
        // Caso base
        if (bajo >= alto)
            return;
        // Se encuentra la posicion correcta del pivote.
        int pivoteIdx = particionar(arreglo, comparador, bajo, alto);
        // Llamadas recursivas.
        quickSort(arreglo, comparador, bajo, pivoteIdx - 1);
        quickSort(arreglo, comparador, pivoteIdx + 1, alto);
    }

    /**
     * Particiona el arreglo recibido  en [menores, pivote, mayores]
     * @param <T> el tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a particionar.
     * @param comparador el comparador para particionar el arreglo.
     * @param bajo el limite inferior del arreglo a particionar.
     * @param alto el limite superior del arreglo a particionar.
     */
    private static <T> void
    particionar(T[] arreglo, Comparator<T> comparador, int bajo, int alto) {
        T pivote = arreglo[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            if (comparador.compare(arreglo[j], pivote) <= 0) {
                i++;
                swap(arreglo, i, j);
            }
        }
        swap(arreglo, i + 1, alto);
        return i + 1;
    }
    
    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length - 1; i++) {
            int posMin = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[posMin]) < 0) {
                    posMin = j;
                }
            }
            if (posMin != i) {
                swap(arreglo, posMin, i);
            }
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /*      
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        busquedaBinaria(arreglo, comparador, 0, arreglo.length - 1);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /*      
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @param inicio el limite inferior del arreglo.
     * @param fin el limite superior del arreglo
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    private static <T> int 
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador, int inicio, int fin) {
        // Caso base
        if (inicio >= bajo) 
            return -1;
        // Indice del elemento a la mitad del arreglo.
        int medio = inicio + (fin - inicio) / 2;
        // El elemento es mayor que el elemento a la mitad del arreglo.
        if (comparador.compare(arreglo[medio], elemento) <= 0) {
            return busquedaBinaria(arreglo, elemento, medio + 1, fin);
        }
        // El elemento es menor que el elemento a la mitad del arreglo.
        if (comparador.compare(arreglo[medio], elemento) > 0) {
            return busquedaBinaria(arreglo, elemento, comparador, inicio, medio);
        }
        // El elemento es igual al elemento a la mitad del arreglo.
        return medio;   
    }
}
