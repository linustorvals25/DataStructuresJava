package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            return (color == Color.ROJO ? "R{" : "N{") + elemento + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return color == vertice.color && super.equals(objeto);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return ((VerticeRojinegro)vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro v = rojinegro(ultimoAgregado);
        v.color = Color.ROJO;
        rebalanceaAgrega(v);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro v = rojinegro((Vertice)busca(elemento));
        if (v == null)
            return;
        elementos--;
        if (v.izquierdo != null && v.derecho != null)
            v = rojinegro(intercambiaEliminable(v));

        VerticeRojinegro hijo = v.izquierdo != null ? rojinegro(v.izquierdo) :
                                                       rojinegro(v.derecho);
        Color colorEliminado = v.color;
        Vertice padre = v.padre;
        reemplaza(v, hijo);
        if (colorEliminado == Color.NEGRO) {
            if (esRojo(hijo))
                hijo.color = Color.NEGRO;
            else
                rebalanceaElimina(hijo, padre);
        }
        if (raiz != null)
            rojinegro(raiz).color = Color.NEGRO;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }

    /* Convierte un vértice en rojinegro, aceptando null para las hojas. */
    private VerticeRojinegro rojinegro(Vertice vertice) {
        return vertice == null ? null : (VerticeRojinegro)vertice;
    }

    private boolean esRojo(VerticeRojinegro vertice) {
        return vertice != null && vertice.color == Color.ROJO;
    }

    private Color color(VerticeRojinegro vertice) {
        return vertice == null ? Color.NEGRO : vertice.color;
    }

    private void reemplaza(Vertice v, Vertice hijo) {
        if (v.padre == null)
            raiz = hijo;
        else if (v.padre.izquierdo == v)
            v.padre.izquierdo = hijo;
        else
            v.padre.derecho = hijo;
        if (hijo != null)
            hijo.padre = v.padre;
    }

    private void rebalanceaAgrega(VerticeRojinegro v) {
        while (v != raiz && esRojo(rojinegro(v.padre))) {
            VerticeRojinegro p = rojinegro(v.padre);
            VerticeRojinegro a = rojinegro(p.padre);
            boolean izquierdo = a.izquierdo == p;
            VerticeRojinegro t = rojinegro(izquierdo ? a.derecho : a.izquierdo);
            if (esRojo(t)) {
                p.color = Color.NEGRO;
                t.color = Color.NEGRO;
                a.color = Color.ROJO;
                v = a;
            } else {
                if (izquierdo && p.derecho == v) {
                    super.giraIzquierda(p);
                    VerticeRojinegro auxiliar = p; p = v; v = auxiliar;
                } else if (!izquierdo && p.izquierdo == v) {
                    super.giraDerecha(p);
                    VerticeRojinegro auxiliar = p; p = v; v = auxiliar;
                }
                a = rojinegro(p.padre);
                p.color = Color.NEGRO;
                a.color = Color.ROJO;
                if (a.izquierdo == p)
                    super.giraDerecha(a);
                else
                    super.giraIzquierda(a);
            }
        }
        rojinegro(raiz).color = Color.NEGRO;
    }

    /* Rebalanceo de eliminación CLRS; x puede ser una hoja null. */
    private void rebalanceaElimina(VerticeRojinegro x, Vertice padre) {
        while (x != raiz && color(x) == Color.NEGRO) {
            if (padre == null)
                break;
            boolean izquierdo = padre.izquierdo == x;
            VerticeRojinegro h = rojinegro(izquierdo ? padre.derecho : padre.izquierdo);
            if (esRojo(h)) {
                h.color = Color.NEGRO;
                rojinegro(padre).color = Color.ROJO;
                if (izquierdo) super.giraIzquierda(padre); else super.giraDerecha(padre);
                h = rojinegro(izquierdo ? padre.derecho : padre.izquierdo);
            }
            VerticeRojinegro cercano = rojinegro(h == null ? null :
                (izquierdo ? h.izquierdo : h.derecho));
            VerticeRojinegro lejano = rojinegro(h == null ? null :
                (izquierdo ? h.derecho : h.izquierdo));
            if (color(cercano) == Color.NEGRO && color(lejano) == Color.NEGRO) {
                if (h != null) h.color = Color.ROJO;
                x = rojinegro(padre);
                padre = padre.padre;
            } else {
                if (color(lejano) == Color.NEGRO) {
                    if (cercano != null) cercano.color = Color.NEGRO;
                    if (h != null) {
                        h.color = Color.ROJO;
                        if (izquierdo) super.giraDerecha(h); else super.giraIzquierda(h);
                    }
                    h = rojinegro(izquierdo ? padre.derecho : padre.izquierdo);
                }
                if (h != null) {
                    h.color = rojinegro(padre).color;
                    VerticeRojinegro extremo = rojinegro(izquierdo ? h.derecho : h.izquierdo);
                    if (extremo != null) extremo.color = Color.NEGRO;
                }
                rojinegro(padre).color = Color.NEGRO;
                if (izquierdo) super.giraIzquierda(padre); else super.giraDerecha(padre);
                x = rojinegro(raiz);
                padre = null;
            }
        }
        if (x != null) x.color = Color.NEGRO;
    }
}
