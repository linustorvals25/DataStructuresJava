package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>
 * Clase para árboles binarios completos.
 * </p>
 *
 * <p>
 * Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.
 * </p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<>();
            if (!esVacia())
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override
        public T next() {
            Vertice v = cola.saca();
            if (v.hayIzquierdo())
                cola.mete(v.izquierdo);
            if (v.hayDerecho())
                cola.mete(v.derecho);
            return v.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() {
        super();
    }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * 
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento nulo");
        if (raiz == null) {
            raiz = new Vertice(elemento);
            elementos++;
            return;
        }

        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice v = cola.saca();
            if (v.izquierdo == null) {
                v.izquierdo = new Vertice(elemento);
                v.izquierdo.padre = v;
                elementos++;
                return;
            }
            if (v.derecho == null) {
                v.derecho = new Vertice(elemento);
                v.derecho.padre = v;
                elementos++;
                return;
            }
            cola.mete(v.izquierdo);
            cola.mete(v.derecho);
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("Elemento nulo");
        if (raiz == null)
            return;
        if (raiz.elemento.equals(elemento)) {
            raiz = null;
            elementos--;
            return;
        }
        Vertice v1 = buscaVertice(elemento);
        if (v1 == null)
            return;
        Vertice v2 = buscaUltimo();
        v1.elemento = v2.elemento;
        if (v1.padre.izquierdo == v1) {
            v1.padre.izquierdo = null;
        } else {
            v1.padre.derecho = null;
        }
        elementos--;
    }

    /**
     * Busca un vértice con el elemento dado.
     * 
     * @param elemento el elemento a buscar
     * @return el vértice con el elemento dado, o <code>null</code> si no se
     *         encuentra
     */
    private Vertice buscaVertice(T elemento) {
        if (raiz == null)
            return null;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice v = cola.saca();
            if (v.elemento.equals(elemento))
                return v;
            if (v.izquierdo != null)
                cola.mete(v.izquierdo);
            if (v.derecho != null)
                cola.mete(v.derecho);
        }
        return null;
    }

    /**
     * Busca el último vértice del árbol en orden BFS.
     * 
     * @return el último vértice del árbol en orden BFS, o <code>null</code> si
     *         el árbol es vacío.
     */
    private Vertice buscaUltimo() {
        if (raiz == null)
            return null;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice v = cola.saca();
            if (v.izquierdo == null && v.derecho == null)
                return v;
            if (v.izquierdo != null)
                cola.mete(v.izquierdo);
            if (v.derecho != null)
                cola.mete(v.derecho);
        }
        return null;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * 
     * @return la altura del árbol.
     */
    @Override
    public int altura() {
        return (int) Math.floor(Math.log(elementos) / Math.log(2));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice v = cola.saca();
            accion.actua(v);
            if (v.izquierdo != null)
                cola.mete(v.izquierdo);
            if (v.derecho != null)
                cola.mete(v.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
