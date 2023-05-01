package grafo;

import java.util.Comparator;
public class PairComparator implements Comparator<Pair<Nodo,Double>> {
    @Override
    public int compare(Pair<Nodo,Double> pair1, Pair<Nodo,Double> pair2) {
        if (pair1.getValue() != pair2.getValue()) {
            return (int) (pair1.getValue() - pair2.getValue());
        } else {
            return pair1.getKey().getName().compareTo(pair2.getKey().getName());
        }
    }

}
