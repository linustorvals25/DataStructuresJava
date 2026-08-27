package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase genérica para listas doblemente ligadas.
 * </p>
 *
 * <p>
 * Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.
 * </p>
 *
 * <p>
 * Las listas no aceptan a <code>null</code> como elemento.
 * </p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No hay elemento siguiente.");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override
        public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException("No hay elemento anterior.");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override
        public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override
        public void end() {
            anterior = rabo;
            siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;
    /* Mensaje al tratar de agregar un elemento nulo a la lista. */
    private final String MSJ_NULL = "La lista no acepta a null como elemento.";
    /* Mensaje al tratar de eliminar cuando la lista esta vacia. */
    private final String MSJ_EMPTY = "La lista esta vacia, no se pueden eliminar elementos.";

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * 
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * 
     * @return el número elementos en la lista.
     */
    @Override
    public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * 
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL);
        Nodo nuevo = new Nodo(elemento);
        if (esVacia()) {
            cabeza = rabo = nuevo;
        } else {
            rabo.siguiente = nuevo;
            nuevo.anterior = rabo;
            rabo = nuevo;
        }
        longitud++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL);
        Nodo nuevo = new Nodo(elemento);
        if (esVacia()) {
            cabeza = rabo = nuevo;
        } else {
            cabeza.anterior = nuevo;
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
        longitud++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * 
     * @param i        el índice dónde insertar el elemento. Si es menor que 0 el
     *                 elemento se agrega al inicio de la lista, y si es mayor o
     *                 igual
     *                 que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException(MSJ_NULL);
        if (i <= 0) {
            agregaInicio(elemento);
            return;
        }
        if (i >= longitud) {
            agregaFinal(elemento);
            return;
        }
        Nodo nuevo = new Nodo(elemento);
        Nodo temp = cabeza;
        for (int j = 0; j < i - 1; j++) {
            temp = temp.siguiente;
        }
        nuevo.anterior = temp;
        nuevo.siguiente = temp.siguiente;
        temp.siguiente.anterior = nuevo;
        temp.siguiente = nuevo;
        longitud++;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        if (elemento == null)
            return;
        Nodo temp = cabeza;
        while (temp != null) {
            if (temp.elemento.equals(elemento)) {
                if (temp == cabeza) {
                    eliminaPrimero();
                    return;
                }
                if (temp == rabo) {
                    eliminaUltimo();
                    return;
                }
                temp.anterior.siguiente = temp.siguiente;
                temp.siguiente.anterior = temp.anterior;
                longitud--;
                return;
            }
            temp = temp.siguiente;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * 
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY);
        T eliminado = cabeza.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        longitud--;
        return eliminado;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * 
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY);
        T eliminado = rabo.elemento;
        if (cabeza == rabo) {
            cabeza = rabo = null;
        } else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        }
        longitud--;
        return eliminado;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * 
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        if (elemento == null)
            return false;
        Nodo temp = cabeza;
        while (temp != null) {
            if (temp.elemento.equals(elemento))
                return true;
            temp = temp.siguiente;
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * 
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversa = new Lista<>();
        Nodo temp = cabeza;
        while (temp != null) {
            reversa.agregaInicio(temp.elemento);
            temp = temp.siguiente;
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * 
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<>();
        Nodo temp = cabeza;
        while (temp != null) {
            copia.agregaFinal(temp.elemento);
            temp = temp.siguiente;
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY);
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * 
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
            throw new NoSuchElementException(MSJ_EMPTY);
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * 
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *                                 igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido("Indice fuera de rango [0, longitud).");
        Nodo temp = cabeza;
        for (int j = 0; j < i; j++) {
            temp = temp.siguiente;
        }
        return temp.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * 
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if (elemento == null)
            return -1;
        Nodo temp = cabeza;
        for (int i = 0; i < longitud; i++) {
            if (temp.elemento.equals(elemento))
                return i;
            temp = temp.siguiente;
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * 
     * @return una representación en cadena de la lista.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo temp = cabeza;
        while (temp != null) {
            sb.append(temp.elemento);
            if (temp.siguiente != null)
                sb.append(", ");
            temp = temp.siguiente;
        }
        return sb.toString() + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Lista<T> lista = (Lista<T>) objeto;
        if (longitud != lista.longitud)
            return false;
        Nodo n1 = cabeza;
        Nodo n2 = lista.cabeza;
        while (n1 != null) {
            if (!n1.elemento.equals(n2.elemento))
                return false;
            n1 = n1.siguiente;
            n2 = n2.siguiente;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * 
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * 
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * 
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Caso base.
        if (this.longitud <= 1)
            return this.copia();
        // Dividir la lista en dos mitades.
        Lista<T> izq = new Lista<>();
        Lista<T> der = new Lista<>();
        Nodo temp = this.cabeza;
        int mitad = this.longitud / 2;
        for (int i = 0; i < longitud; i++) {
            if (i < mitad) {
                izq.agregaFinal(temp.elemento);
            } else {
                der.agregaFinal(temp.elemento);
            }
            temp = temp.siguiente;
        }
        // Ordenar recursivamente cada mitad.
        izq = izq.mergeSort(comparador);
        der = der.mergeSort(comparador);
        // Mezclar las dos mitades ordnadas.
        return mezcla(comparador, izq, der);
    }

    /**
     * Metoda auxiliar a mergeSort:
     * Mezcla las dos listas (ordenadas) recibidas un una nueva lista ordenada.
     * 
     * @param comparador el comparador que se usara para hacer el ordanamiento,
     * @param lista1     la primer lista a mezclar.
     * @param lista2     la segunda lista a mezclar.
     * @return una nueva lista que contiene a los elementos de <code>lista1</code> y
     *         <code>lista2</code> pero ordenados.
     */
    private Lista<T> mezcla(Comparator<T> comparador, Lista<T> lista1, Lista<T> lista2) {
        Lista<T> mezcla = new Lista<>();
        Nodo n1 = lista1.cabeza;
        Nodo n2 = lista2.cabeza;
        while (n1 != null && n2 != null) {
            if (comparador.compare(n1.elemento, n2.elemento) <= 0) {
                mezcla.agregaFinal(n1.elemento);
                n1 = n1.siguiente;
            } else {
                mezcla.agregaFinal(n2.elemento);
                n2 = n2.siguiente;
            }
        }
        while (n1 != null) {
            mezcla.agregaFinal(n1.elemento);
            n1 = n1.siguiente;
        }
        while (n2 != null) {
            mezcla.agregaFinal(n2.elemento);
            n2 = n2.siguiente;
        }
        return mezcla;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * 
     * @param <T>   tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * 
     * @param elemento   el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo temp = cabeza;
        while (temp != null) {
            if (comparador.compare(temp.elemento, elemento) == 0)
                return true;
            temp = temp.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * 
     * @param <T>      tipo del que puede ser la lista.
     * @param lista    la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>> boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
