package mx.unam.ciencias.edd;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p> Un árbol instancia de esta clase siempre cumple que: </p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
        extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;
        private Vertice actual;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<>();
            actual = raiz;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacia() || actual != null;
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No hay más elementos en el árbol.");
            while (actual != null) {
                pila.mete(actual);
                actual = actual.izquierdo;
            }
            Vertice v = pila.saca();
            actual = v.derecho;
            return v.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento nulo");
        Vertice v = nuevoVertice(elemento);
        elementos++;
        ultimoAgregado = v; // Guardamos el último agregado
        if (raiz == null) {
            raiz = v;
            return;
        }
        Vertice actual = raiz;
        while (true) {
            if (elemento.compareTo(actual.elemento) < 0) {
                if (actual.izquierdo == null) {
                    actual.izquierdo = v;
                    v.padre = actual;
                    return;
                }
                actual = actual.izquierdo;
            } else {
                if (actual.derecho == null) {
                    actual.derecho = v;
                    v.padre = actual;
                    return;
                }
                actual = actual.derecho;
            }
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        if (elemento == null)
            return;
        Vertice v = (Vertice) busca(elemento);
        if (v == null)
            return;
        if (v.hayIzquierdo() && v.hayDerecho()) {
            v = intercambiaEliminable(v);
        }
        eliminaVertice(v);
        elementos--;
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        if (!vertice.hayIzquierdo() || !vertice.hayDerecho())
            return vertice;
        Vertice izquierdo = vertice.izquierdo;
        while (izquierdo.hayDerecho()) {
            izquierdo = izquierdo.derecho;
        }
        vertice.elemento = izquierdo.elemento;
        return izquierdo;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice padre = vertice.padre;
        Vertice hijo = null;
        if (vertice.hayIzquierdo())
            hijo = vertice.izquierdo;
        else
            hijo = vertice.derecho;
        if (padre == null)
            raiz = hijo;
        else if (padre.izquierdo == vertice)
            padre.izquierdo = hijo;
        else
            padre.derecho = hijo;
        if (hijo != null)
            hijo.padre = padre;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        if (elemento == null)
            return null;
        Vertice v = raiz;
        while (v != null) {
            if (v.elemento.equals(elemento))
                return v;
            if (elemento.compareTo(v.elemento) < 0)
                v = v.izquierdo;
            else
                v = v.derecho;
        }
        return null;
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayIzquierdo())
            return;
        Vertice v = (Vertice) vertice;
        Vertice padre = v.padre;
        Vertice izquierdo = v.izquierdo;
        v.izquierdo = izquierdo.derecho;
        if (izquierdo.derecho != null)
            izquierdo.derecho.padre = v;
        izquierdo.derecho = v;
        izquierdo.padre = padre;
        v.padre = izquierdo;
        if (padre == null)
            raiz = izquierdo;
        else if (padre.izquierdo == v)
            padre.izquierdo = izquierdo;
        else
            padre.derecho = izquierdo;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayDerecho())
            return;
        Vertice v = (Vertice) vertice;
        Vertice padre = v.padre;
        Vertice derecho = v.derecho;
        v.derecho = derecho.izquierdo;
        if (derecho.izquierdo != null)
            derecho.izquierdo.padre = v;
        derecho.izquierdo = v;
        derecho.padre = padre;
        v.padre = derecho;
        if (padre == null)
            raiz = derecho;
        else if (padre.izquierdo == v)
            padre.izquierdo = derecho;
        else
            padre.derecho = derecho;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Pila<Vertice> pila = new Pila<>();
        pila.mete(raiz);
        while (!pila.esVacia()) {
            Vertice v = pila.saca();
            accion.actua(v);
            if (v.hayDerecho())
                pila.mete(v.derecho);
            if (v.hayIzquierdo())
                pila.mete(v.izquierdo);
        }
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Pila<Vertice> pila = new Pila<>();
        Vertice actual = raiz;
        while (!pila.esVacia() || actual != null) {
            while (actual != null) {
                pila.mete(actual);
                actual = actual.izquierdo;
            }
            actual = pila.saca();
            accion.actua(actual);
            actual = actual.derecho;
        }
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Pila<Vertice> pila1 = new Pila<>();
        Pila<Vertice> pila2 = new Pila<>();
        pila1.mete(raiz);
        while (!pila1.esVacia()) {
            Vertice v = pila1.saca();
            pila2.mete(v);
            if (v.hayIzquierdo())
                pila1.mete(v.izquierdo);
            if (v.hayDerecho())
                pila1.mete(v.derecho);
        }
        while (!pila2.esVacia()) {
            Vertice v = pila2.saca();
            accion.actua(v);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa una representación en cadena del árbol ordenado, dibujando su
     * estructura mediante ramas. Un árbol vacío se representa con la cadena
     * vacía.
     *
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /* Método auxiliar recursivo que dibuja el subárbol de vertice. */
    private String toString(Vertice vertice, int nivel, boolean[] ramas) {
        StringBuilder sb = new StringBuilder();
        sb.append(vertice.elemento).append("\n");
        boolean[] ramasHijo = Arrays.copyOf(ramas, nivel + 1);
        if (vertice.hayIzquierdo() && vertice.hayDerecho()) {
            ramasHijo[nivel] = true;
            sb.append(dibujaEspacios(nivel, ramasHijo));
            sb.append("├─›");
            sb.append(toString(vertice.izquierdo, nivel + 1, ramasHijo));
            sb.append(dibujaEspacios(nivel, ramasHijo));
            ramasHijo[nivel] = false;
            sb.append("└─»");
            sb.append(toString(vertice.derecho, nivel + 1, ramasHijo));
        } else if (vertice.hayIzquierdo()) {
            sb.append(dibujaEspacios(nivel, ramasHijo));
            sb.append("└─›");
            ramasHijo[nivel] = false;
            sb.append(toString(vertice.izquierdo, nivel + 1, ramasHijo));
        } else if (vertice.hayDerecho()) {
            sb.append(dibujaEspacios(nivel, ramasHijo));
            sb.append("└─»");
            ramasHijo[nivel] = false;
            sb.append(toString(vertice.derecho, nivel + 1, ramasHijo));
        }
        return sb.toString();
    }

    /* Dibuja el prefijo de espacios/barras verticales para el nivel dado. */
    private String dibujaEspacios(int nivel, boolean[] ramas) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++)
            sb.append(ramas[i] ? "│  " : "   ");
        return sb.toString();
    }
}
