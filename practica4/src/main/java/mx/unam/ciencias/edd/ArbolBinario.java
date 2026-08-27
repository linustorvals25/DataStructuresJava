package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>
 * Clase abstracta para árboles binarios genéricos.
 * </p>
 *
 * <p>
 * La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.
 * </p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        protected T elemento;
        /** El padre del vértice. */
        protected Vertice padre;
        /** El izquierdo del vértice. */
        protected Vertice izquierdo;
        /** El derecho del vértice. */
        protected Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        protected Vertice(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * 
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayPadre() {
            return this.padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * 
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayIzquierdo() {
            return this.izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * 
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override
        public boolean hayDerecho() {
            return this.derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * 
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override
        public VerticeArbolBinario<T> padre() {
            if (!this.hayPadre()) {
                throw new NoSuchElementException("El vértice no tiene padre.");
            }
            return this.padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * 
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override
        public VerticeArbolBinario<T> izquierdo() {
            if (!this.hayIzquierdo()) {
                throw new NoSuchElementException("El vértice no tiene izquierdo.");
            }
            return this.izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * 
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override
        public VerticeArbolBinario<T> derecho() {
            if (!this.hayDerecho()) {
                throw new NoSuchElementException("El vértice no tiene derecho.");
            }
            return this.derecho;
        }

        /**
         * Regresa la altura del vértice.
         * 
         * @return la altura del vértice.
         */
        @Override
        public int altura() {
            if (this.hayIzquierdo() && this.hayDerecho()) {
                return 1 + Math.max(this.izquierdo().altura(), this.derecho().altura());
            } else if (this.hayIzquierdo()) {
                return 1 + this.izquierdo().altura();
            } else if (this.hayDerecho()) {
                return 1 + this.derecho().altura();
            } else {
                return 0;
            }
        }

        /**
         * Regresa la profundidad del vértice.
         * 
         * @return la profundidad del vértice.
         */
        @Override
        public int profundidad() {
            if (!this.hayPadre()) {
                return 0;
            }
            return 1 + this.padre().profundidad();
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * 
         * @return el elemento al que apunta el vértice.
         */
        @Override
        public T get() {
            return this.elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            Vertice vertice = (Vertice) objeto;
            return (this.elemento.equals(vertice.elemento) &&
                    (this.izquierdo == null ? vertice.izquierdo == null : this.izquierdo.equals(vertice.izquierdo)) &&
                    (this.derecho == null ? vertice.derecho == null : this.derecho.equals(vertice.derecho)));
        }

        /**
         * Regresa una representación en cadena del vértice.
         * 
         * @return una representación en cadena del vértice.
         */
        @Override
        public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol
     *                  binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T elemento : coleccion)
            this.agrega(elemento);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * 
     * @return la altura del árbol.
     */
    public int altura() {
        if (this.esVacia()) {
            return -1;
        }
        return this.raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * 
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        return busca(elemento) != null;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * 
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        return buscaAux(raiz, elemento);
    }

    private VerticeArbolBinario<T> buscaAux(Vertice v, T elemento) {
        if (v == null)
            return null;
        if (v.elemento.equals(elemento))
            return v;
        VerticeArbolBinario<T> izquierdo = buscaAux(v.izquierdo, elemento);
        if (izquierdo != null)
            return izquierdo;
        return buscaAux(v.derecho, elemento);
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * 
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (raiz == null) {
            throw new NoSuchElementException("El árbol está vacío");
        }
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override
    public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>) objeto;
        // Manejo seguro de null en raíz
        if (this.raiz == null && arbol.raiz == null)
            return true;
        if (this.raiz == null || arbol.raiz == null)
            return false;
        return this.raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        if (raiz == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(raiz.toString());
        sb.append("\n");
        sb.append(toStringHijos(raiz, ""));
        return sb.toString();
    }

    /**
     * Método auxiliar recursivo que dibuja, con caracteres de línea, a los
     * hijos del vértice recibido. El primer hijo (o el único, si sólo hay
     * uno) se dibuja como la última rama.
     * 
     * @param vertice el vértice cuyos hijos se van a dibujar.
     * @param prefijo el prefijo (las ramas ya dibujadas) que hay que
     *                anteponer a cada línea.
     * @return la representación en cadena de los hijos del vértice.
     */
    private String toStringHijos(Vertice vertice, String prefijo) {
        StringBuilder sb = new StringBuilder();
        if (vertice.izquierdo != null && vertice.derecho != null) {
            sb.append(prefijo).append("├─›").append(vertice.izquierdo.toString()).append("\n");
            sb.append(toStringHijos(vertice.izquierdo, prefijo + "│  "));
            sb.append(prefijo).append("└─»").append(vertice.derecho.toString()).append("\n");
            sb.append(toStringHijos(vertice.derecho, prefijo + "   "));
        } else if (vertice.izquierdo != null) {
            sb.append(prefijo).append("└─›").append(vertice.izquierdo.toString()).append("\n");
            sb.append(toStringHijos(vertice.izquierdo, prefijo + "   "));
        } else if (vertice.derecho != null) {
            sb.append(prefijo).append("└─»").append(vertice.derecho.toString()).append("\n");
            sb.append(toStringHijos(vertice.derecho, prefijo + "   "));
        }
        return sb.toString();
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * 
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *                            Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice) vertice;
    }
}
