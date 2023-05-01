package grafo;

import java.util.Comparator;

class HeuristicComparator implements Comparator<Nodo> {
    @Override
    public int compare(Nodo nodo1, Nodo nodo2) {
        if (nodo1.getPeso() != nodo2.getPeso()) {
            return nodo1.getPeso() - nodo2.getPeso();
        } else {
            return nodo1.getName().compareTo(nodo2.getName());
        }
    }
}
