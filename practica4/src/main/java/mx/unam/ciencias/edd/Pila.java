package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo temp = cabeza;
        while (temp != null) {
            sb.append(elemento + "\n");
            temp = temp.siguiente;
        }
        return sb.toString();
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null)
            return IllegalArgumentException("La Pila no acepta a null como elemento.");
        Nodo nuevo = new Nodo(elemento);
        rabo.siguiente = nuevo;
        rabo = nuevo;
    }
}
