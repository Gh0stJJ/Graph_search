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
                //Verificar si el nodo ya fue visitado o si ya esta en la lista de nodos a visitar
                for (Nodo hijo : hijos_ord) {
                    if (!hijo.isVisited() && !nodos_a_visitar.contains(hijo)){
                        nodos_a_visitar.add(hijo);
                    }
                }

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
                    //Verificar si el nodo ya fue visitado o si ya esta en la lista de nodos a visitar
                    if (!way.getDestination().isVisited() && !queue.contains(way.getDestination())) {
                        temp.add(0, way.getDestination());
                    }
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

        while (!cola.isEmpty()) {
            //Imprimir la cola
            System.out.println("Cola: " + cola);
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
        System.out.println("----Profundidad Iterativa ---- Nodo Inicial: " + nodo_inicial + " Nodo Final: " + nodo_final);
       while (true) {
           System.out.println("Profundidad actual: " + profundidad_actual);
            Pair<ArrayList<Nodo>, Boolean> resultado = busquedaProfundidadLimitada(nodo_inicial, nodo_final, profundidad_actual);
            nodos_visitados.addAll(resultado.getKey());
            System.out.println("Extrae  "+ resultado.getKey());
           if (resultado.getValue()) {
               return nodos_visitados;
           }
            profundidad_actual++;
        }
    }

    //Busqueda bidireccional

    //Busqueda bidireccional por anchura

    /*
        * Realiza una búsqueda bidireccional por anchura desde un nodo inicial hasta un nodo final,
        * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
        * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */
    public void busquedaBidireccionalAnchura(String nodo_inicial, String nodo_final){
        System.out.println("----Busqueda Bidireccional por Anchura ---- Nodo Inicial: " + nodo_inicial + " Nodo Final: " + nodo_final);
        ArrayList<Nodo> extrae_inicio = new ArrayList<>();
        ArrayList<Nodo> extrae_final = new ArrayList<>();

        ArrayList<ArrayList<Nodo>> total_colaA = new ArrayList<>();
        ArrayList<ArrayList<Nodo>> total_colaB = new ArrayList<>();
        ArrayList<Nodo> cola = new ArrayList<>();
        ArrayList<Nodo> cola2 = new ArrayList<>();
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);
        cola.add(nodo_inicial_obj);
        cola2.add(nodo_final_obj);
        while (!cola.isEmpty() && !cola2.isEmpty()){
            //Pasar un clon de la cola a la lista de colas
            total_colaA.add((ArrayList<Nodo>) cola.clone());
            //Primera cola
            Nodo nodo_actual = cola.get(0);
            cola.remove(0);

            extrae_inicio.add(nodo_actual);

            if (nodo_actual.getName().equals(nodo_final_obj.getName())){
                break;
            }
            ArrayList<Nodo> hijos_ord = new ArrayList<>();
            for (Edge way : nodo_actual.getWays()) {
                //Verificar si ya fueron añadidos a la cola o fueron extraidos
                if (!cola.contains(way.getDestination())&& !extrae_inicio.contains(way.getDestination())){
                    hijos_ord.add(way.getDestination());
                }
            }
            Collections.sort(hijos_ord);
            cola.addAll(hijos_ord);


            //Segunda cola
            total_colaB.add((ArrayList<Nodo>) cola2.clone());
            Nodo nodo_actual2 = cola2.get(0);
            cola2.remove(0);

            extrae_final.add(nodo_actual2);

            if (nodo_actual2.getName().equals(nodo_inicial_obj.getName())){
                break;
            }
            ArrayList<Nodo> hijos_ord2 = new ArrayList<>();
            for (Edge way : nodo_actual2.getWays()) {
                //Verificar si ya fueron añadidos a la cola
                if (!cola2.contains(way.getDestination()) && !extrae_final.contains(way.getDestination())){
                    hijos_ord2.add(way.getDestination());
                }
            }
            Collections.sort(hijos_ord2);
            cola2.addAll(hijos_ord2);
            //Pasar un clon de la cola a la lista de colas


            //Verificar si hay un nodo en comun
            for (Nodo nodo : cola) {
                if (cola2.contains(nodo)){
                    //Enviar ultima cola
                    total_colaA.add((ArrayList<Nodo>) cola.clone());
                    total_colaB.add((ArrayList<Nodo>) cola2.clone());

                    //Imprimir las colas
                    System.out.println("Cola desde Nodo Inicial: ");

                    for (ArrayList<Nodo> lista : total_colaA) {
                        System.out.println(lista);
                    }
                    System.out.println("Extrae Inicio: " + extrae_inicio);
                    System.out.println("----------------------------------------------");
                    System.out.println("Cola desde Nodo Final: " );
                    for (ArrayList<Nodo> lista : total_colaB) {
                        System.out.println(lista);
                    }
                    System.out.println("Extrae Final: " + extrae_final);
                    System.out.println("----------------------------------------------");
                    //Nodo en comun
                    System.out.println("Nodo en comun: " + nodo.getName());
                    //Vaciar las colas
                    cola.clear();
                    cola2.clear();
                    break;
                }
            }

        }

    }

    //Busqueda bidireccional por profundidad

    /*
     * Realiza una búsqueda bidireccional por profundidad desde un nodo inicial hasta un nodo final,
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */
    public void busquedaBidireccionalProfundidad(String nodo_inicial, String nodo_final) {
        ArrayList<Nodo> extrae_inicio = new ArrayList<>();
        ArrayList<Nodo> extrae_final = new ArrayList<>();

        ArrayList<ArrayList<Nodo>> total_colaA = new ArrayList<>();
        ArrayList<ArrayList<Nodo>> total_colaB = new ArrayList<>();
        ArrayList<Nodo> cola = new ArrayList<>();
        ArrayList<Nodo> cola2 = new ArrayList<>();
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);
        cola.add(nodo_inicial_obj);
        cola2.add(nodo_final_obj);
        while (!cola.isEmpty() && !cola2.isEmpty()){
            //Pasar un clon de la cola a la lista de colas
            total_colaA.add((ArrayList<Nodo>) cola.clone());
            //Primera cola
            Nodo nodo_actual = cola.get(0);
            cola.remove(0);

            extrae_inicio.add(nodo_actual);

            if (nodo_actual.getName().equals(nodo_final_obj.getName())){
                break;
            }
            ArrayList<Nodo> hijos_ord = new ArrayList<>();
            for (Edge way : nodo_actual.getWays()) {
                //Verificar si ya fueron añadidos a la cola o fueron extraidos
                if (!cola.contains(way.getDestination())&& !extrae_inicio.contains(way.getDestination())){
                    hijos_ord.add(way.getDestination());
                }
            }
            Collections.sort(hijos_ord);
            cola.addAll(0,hijos_ord);


            //Segunda cola
            total_colaB.add((ArrayList<Nodo>) cola2.clone());
            Nodo nodo_actual2 = cola2.get(0);
            cola2.remove(0);

            extrae_final.add(nodo_actual2);

            if (nodo_actual2.getName().equals(nodo_inicial_obj.getName())){
                break;
            }
            ArrayList<Nodo> hijos_ord2 = new ArrayList<>();
            for (Edge way : nodo_actual2.getWays()) {
                //Verificar si ya fueron añadidos a la cola
                if (!cola2.contains(way.getDestination()) && !extrae_final.contains(way.getDestination())){
                    hijos_ord2.add(way.getDestination());
                }
            }
            Collections.sort(hijos_ord2);
            cola2.addAll(0,hijos_ord2);
            //Pasar un clon de la cola a la lista de colas


            //Verificar si hay un nodo en comun
            for (Nodo nodo : cola) {
                if (cola2.contains(nodo)){
                    //Enviar ultima cola
                    total_colaA.add((ArrayList<Nodo>) cola.clone());
                    total_colaB.add((ArrayList<Nodo>) cola2.clone());

                    //Imprimir las colas
                    System.out.println("Cola desde Nodo Inicial: ");

                    for (ArrayList<Nodo> lista : total_colaA) {
                        System.out.println(lista);
                    }
                    System.out.println("Extrae Inicio: " + extrae_inicio);
                    System.out.println("----------------------------------------------");
                    System.out.println("Cola desde Nodo Final: " );
                    for (ArrayList<Nodo> lista : total_colaB) {
                        System.out.println(lista);
                    }
                    System.out.println("Extrae Final: " + extrae_final);
                    System.out.println("----------------------------------------------");
                    //Nodo en comun
                    System.out.println("Nodo en comun: " + nodo.getName());
                    //Vaciar las colas
                    cola.clear();
                    cola2.clear();
                    break;
                }
            }

        }


    }

    //Busqueda coste uniforme
    /*
        * Realiza una búsqueda por coste uniforme desde un nodo inicial hasta un nodo final,
        * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
        * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */

    public void busquedaCosteUniforme(String nodo_inicial, String nodo_final) {
        ArrayList<Pair<Nodo,Double>> extrae = new ArrayList<>();
        //Cola
        ArrayList<Pair<Nodo,Double>> cola = new ArrayList<>();
        Nodo nodo_inicial_obj = graph.findNode(nodo_inicial);
        Nodo nodo_final_obj = graph.findNode(nodo_final);
        cola.add(new Pair<>(nodo_inicial_obj,0.0));
        Map<Nodo,Double> costo_acumulado = new HashMap<>();
        costo_acumulado.put(nodo_inicial_obj,0.0);
        //System.out.println("[" + cola+ ":"+ cola.);
        while (!cola.isEmpty()) {
            System.out.println(cola);
            // Se extrae el nodo con menor costo acumulado de la cola.
            Pair<Nodo, Double> nodoActual = cola.stream().min(Comparator.comparing(Pair::getValue)).get();
            cola.remove(nodoActual);
            Nodo nodo = nodoActual.getKey();
            // Si llegamos al nodo final, se termina la búsqueda.
            if (nodo.equals(nodo_final_obj)) {
                // Hacer algo con la solución encontrada
                return;
            }
            // Para cada vecino del nodo actual, se actualiza el costo acumulado y se añade a la cola.
            for (Edge arista : nodo.getWays()) {
                Nodo vecino = arista.getDestination();
                double costo = arista.getDistance();
                double costoAcumuladoNuevo = costo_acumulado.get(nodo) + costo;
                // Si el vecino no ha sido visitado o si se encontró un camino más corto, se actualiza su costo acumulado y se añade a la cola.
                if (!costo_acumulado.containsKey(vecino) || costoAcumuladoNuevo < costo_acumulado.get(vecino)) {
                    costo_acumulado.put(vecino, costoAcumuladoNuevo);
                    cola.add(new Pair<>(vecino, costoAcumuladoNuevo));
                }
            }
        }





    }




}
