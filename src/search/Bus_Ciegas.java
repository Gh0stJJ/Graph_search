package search;

import grafo.Edge;
import grafo.Grafo;
import grafo.Nodo;
import grafo.Pair;

import java.util.*;

public class Bus_Ciegas {

    private Grafo graph;
    public Bus_Ciegas(Grafo graph) {
        this.graph = graph;
    }

    /**
     * Busqueda en anchura
     * @param nodo_inicial Nombre del nodo inicial
     * @param nodo_final Nombre del nodo final
     * @return ArrayList<Nodo> con los nodos visitados
     */
    public ArrayList<Nodo> busquedaAnchura(String nodo_inicial, String nodo_final){
        //TODO
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        ArrayList<Nodo> nodos_a_visitar = new ArrayList<>();
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);
        nodos_a_visitar.add(nodo_inicial_obj);
        while (!nodos_a_visitar.isEmpty()){
            Nodo nodo_actual = nodos_a_visitar.get(0);
            nodos_a_visitar.remove(0);
            if (!nodo_actual.isVisited()){
                nodos_visitados.add(nodo_actual);
                nodo_actual.setVisited(true);
                if (nodo_actual.getName().equals(nodo_final_obj.getName())){
                    break;
                }
                ArrayList<Nodo> hijos_ord = new ArrayList<>();
                for (Edge way : nodo_actual.getWays()) {
                    hijos_ord.add(way.getDestination());
                }
                Collections.sort(hijos_ord);
                nodos_a_visitar.addAll(hijos_ord);
            }
        }

        return nodos_visitados;
    }

    /**
     * Realiza una búsqueda en profundidad desde un nodo inicial hasta un nodo final,
     * devolviendo una lista con los nodos visitados durante la búsqueda.
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     * @return Una lista con los nodos visitados durante la búsqueda en profundidad,
     *         en el orden en que fueron visitados.
     */
    public ArrayList<Nodo> busquedaProfundidad(String nodo_inicial, String nodo_final){
        //TODO
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        ArrayList<Nodo> queue = new ArrayList<>();
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);

        queue.add(nodo_inicial_obj);
        while (!queue.isEmpty()){
            Nodo nodo_actual = queue.get(0);
            queue.remove(0);
            if (!nodo_actual.isVisited()){
                nodos_visitados.add(nodo_actual);
                nodo_actual.setVisited(true);
                if (nodo_actual.getName().equals(nodo_final_obj.getName())){
                    break;
                }
                ArrayList<Nodo> temp = new ArrayList<>();
                for (Edge way : nodo_actual.getWays()) {
                    temp.add(0,way.getDestination());
                }
                //Ordenar la lista de hijos
                Collections.sort(temp);

                queue.addAll(0,temp);
            }
        }
        return nodos_visitados;
    }

    /*
     * Realiza una búsqueda en profundidad limitada desde un nodo inicial hasta un nodo final,
     * devolviendo una lista con los nodos visitados durante la búsqueda.
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     * @param profundidad_maxima La profundidad máxima para la búsqueda en profundidad iterativa.
     * @return Una lista con los nodos visitados durante la búsqueda en profundidad por nivel,
     *         en el orden en que fueron visitados.
     */
    public Pair<ArrayList<Nodo>, Boolean> busquedaProfundidadLimitada(String nodo_inicial, String nodo_final, int profundidad_maxima) {
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();

        ArrayList<Pair<Nodo, Integer>> cola = new ArrayList<>(); // Usamos Pair para guardar el nodo y su nivel
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);

        cola.add(new Pair<>(nodo_inicial_obj, 0)); // Agregamos el nodo inicial con nivel 0
        int profundidad_actual = 0;

        while (!cola.isEmpty()) {
            Pair<Nodo, Integer> nodo_actual_par = cola.remove(0);
            Nodo nodo_actual = nodo_actual_par.getKey();
            int nivel_actual = nodo_actual_par.getValue();

            nodos_visitados.add(nodo_actual);

            if (nodo_actual.getName().equals(nodo_final_obj.getName())) {

                return new Pair<>(nodos_visitados, true);
            }

            if (nivel_actual < profundidad_maxima) { // Verificamos que no exceda el nivel máximo
                ArrayList<Nodo> temp = new ArrayList<>();
                for (Edge way : nodo_actual.getWays()) {
                    temp.add(way.getDestination());
                }
                //Ordenar la lista de hijos
                Collections.sort(temp);
                //Invertir la lista de hijos
                Collections.reverse(temp);
                // Agregamos los nodos hijos con nivel + 1
                for (Nodo hijo : temp) {
                    cola.add(0,new Pair<>(hijo, nivel_actual + 1));
                }

            }
        }

        return new Pair<>(nodos_visitados, false);
    }

    /*
     * Realiza una búsqueda en profundidad iterativa desde un nodo inicial hasta un nodo final,
     * devolviendo una lista con los nodos visitados durante la búsqueda.
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     * @return Una lista con los nodos visitados durante la búsqueda en profundidad iterativa,
     *         en el orden en que fueron visitados.
     */
    public ArrayList<Nodo> busquedaProfundidadIterativa(String nodo_inicial, String nodo_final) {
        //TODO
        int profundidad_actual = 0;
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
       while (true) {
            Pair<ArrayList<Nodo>, Boolean> resultado = busquedaProfundidadLimitada(nodo_inicial, nodo_final, profundidad_actual);
            nodos_visitados.addAll(resultado.getKey());
           if (resultado.getValue()) {
               return nodos_visitados;
           }
            profundidad_actual++;
        }
    }









}
