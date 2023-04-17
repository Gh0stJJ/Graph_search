package grafo;

import java.util.Comparator;

class HeuristicComparator implements Comparator<Nodo> {
    @Override
    public int compare(Nodo nodo1, Nodo nodo2) {
        if (nodo1.getPeso() < nodo2.getPeso()) {
            return 1; // nodo1 tiene mayor peso heurístico, por lo que se coloca primero en la cola
        } else if (nodo1.getPeso() > nodo2.getPeso()) {
            return -1; // nodo2 tiene mayor peso heurístico, por lo que se coloca primero en la cola
        } else {
            return 0; // los nodos tienen el mismo peso heurístico
        }
    }
}
