package grafo;

import java.util.*;

public class Grafo {
    private ArrayList<Nodo> nodes;


    public Grafo() {
        nodes = new ArrayList<>();

    }

    public void addNode(Nodo node) {
        //add node if it doesn't exist
       boolean exists = false;
        for (Nodo n : nodes) {
            if (n.getName().equals(node.getName())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            nodes.add(node);
            node.setId(String.valueOf(nodes.indexOf(node)));
            nodes.set(nodes.indexOf(node), node);
        }
    }

    //Find node by name
    public Nodo findNode(String name){
        for (Nodo node : nodes) {
            if (node.getName().equals(name)) {
                return node; //Hacer tree map
            }
        }
        return null;
    }


    public void connectNodes(Nodo node1, Nodo node2, double distance){
        for (Nodo node : nodes) {
            if (node.getName().equals(node1.getName())) {
                node.addWay(node2, distance);
            }
        }
    }

    public void addWeight(String nam_node, int peso){
        findNode(nam_node).setPeso(peso);
    }


    public ArrayList<Nodo> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges(){
        ArrayList<Edge> edges = new ArrayList<>();
        for (Nodo node : nodes) {
            edges.addAll(node.getWays());
        }
        return edges;
    }


    public void printNodes() {
        for (Nodo node : nodes) {
            System.out.println("Nodo: " + node.getName());
            System.out.println("ID: " + node.getId());
            System.out.println("Caminos: ");
            for (Edge way : node.getWays()) {
                System.out.println("Nombre: " + way.getDestination().getName() + "  Distancia: " + way.getDistance());
            }
            System.out.println("-----------------------------");
        }
    }
    public void flush(){
        for (Nodo node : nodes) {
            node.setVisited(false);
        }
    }

    //Obtener profundidad de un nodo
    public int getDepth(String root, String dest) {
        flush();
        ArrayList<Pair<Nodo, Integer>> cola = new ArrayList<>(); // Usamos Pair para guardar el nodo y su nivel
        Nodo nodo_inicial_obj = findNode(root);
        Nodo nodo_final_obj = findNode(dest);

        cola.add(new Pair<>(nodo_inicial_obj, 0)); // Agregamos el nodo inicial con nivel 0
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        while (!cola.isEmpty()) {
            Pair<Nodo, Integer> nodo_actual_par = cola.remove(0);
            Nodo nodo_actual = nodo_actual_par.getKey();
            int nivel_actual = nodo_actual_par.getValue();
            nodo_actual.setVisited(true);
            if (nodo_actual.getName().equals(nodo_final_obj.getName())) {
                return nivel_actual;
            }
            ArrayList<Nodo> temp = new ArrayList<>();
            for (Edge way : nodo_actual.getWays()) {
                temp.add(way.getDestination());
            }
            // Agregamos los nodos hijos con nivel + 1
            for (Nodo hijo : temp) {
                boolean exists = false;
                for (Pair<Nodo, Integer> pair : cola) {
                    if (pair.getKey().getName().equals(hijo.getName())) {
                        exists = true;
                        break;
                    }
                }
                //Verificar si el nodo ya fue visitado o si ya esta en la lista de nodos a visitar

                if (!hijo.isVisited() && !exists) {
                    cola.add(0, new Pair<>(hijo, nivel_actual + 1));
                }
            }

        }

        return -1;

    }




    //Busquedas a ciegas

    /**
     * Busqueda en anchura
     * @param nodo_inicial Nombre del nodo inicial
     * @param nodo_final Nombre del nodo final
     * @return Branching factor
     */
    public void busquedaAnchura(String nodo_inicial, String nodo_final){
        //TODO
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        ArrayList<Nodo> nodos_a_visitar = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        nodos_a_visitar.add(nodo_inicial_obj);
        int branching_factor = 0;
        System.out.println("Cola: ");

        while (!nodos_a_visitar.isEmpty()){
            for (Nodo nodo : nodos_a_visitar) {
                System.out.print("|"+nodo.getName() + "|");
            }
            System.out.println();
            Nodo nodo_actual = nodos_a_visitar.get(0);
            nodos_a_visitar.remove(0);
            if (!nodo_actual.isVisited()){
                nodos_visitados.add(nodo_actual);
                nodo_actual.setVisited(true);
                if (nodo_actual.getName().equals(nodo_final_obj.getName())){
                    break;
                }
                ArrayList<Nodo> hijos_ord = new ArrayList<>();
                int hijos = 0;
                for (Edge way : nodo_actual.getWays()) {
                    hijos_ord.add(way.getDestination());
                    hijos++;
                }
                if (hijos > branching_factor){
                    branching_factor = hijos;
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

        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print("|"+nodo.getName() + "|");
        }
        //return branching_factor;
    }

    /**
     * Realiza una búsqueda en profundidad desde un nodo inicial hasta un nodo final,
     * devolviendo una lista con los nodos visitados durante la búsqueda.
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */
    public void busquedaProfundidad(String nodo_inicial, String nodo_final){
        //TODO
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        ArrayList<Nodo> queue = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        System.out.println("Cola: ");
        queue.add(nodo_inicial_obj);
        while (!queue.isEmpty()){
            Nodo nodo_actual = queue.get(0);
            for (Nodo nodo : queue) {
                System.out.print("|"+nodo.getName() + "|");
            }
            System.out.println();
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
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print("|"+nodo.getName() + "|");
        }
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
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);

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
                    boolean exists = false;
                    for (Pair<Nodo, Integer> pair : cola) {
                        if (pair.getKey().getName().equals(hijo.getName())){
                            exists = true;
                            break;
                        }
                    }
                    //Verificar si el nodo ya fue visitado o si ya esta en la lista de nodos a visitar

                    if (!nodos_visitados.contains(hijo) && !exists) {
                        cola.add(0, new Pair<>(hijo, nivel_actual + 1));
                    }
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
     * @return profundidad La profundidad en la que se encontró el nodo final.
     */
    public int busquedaProfundidadIterativa(String nodo_inicial, String nodo_final) {
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
                return profundidad_actual;
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
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        cola.add(nodo_inicial_obj);
        cola2.add(nodo_final_obj);
        while (!cola.isEmpty() && !cola2.isEmpty()){
            //Pasar un clon de la cola a la lista de colas
            total_colaA.add((ArrayList<Nodo>) cola.clone());
            //Primera cola
            Nodo nodo_actual = cola.get(0);
            cola.remove(0);

            extrae_inicio.add(nodo_actual);

            if (nodo_actual.getName().equals(nodo_final_obj.getName())){ //Si el nodo actual es el nodo final
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
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
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

    public Double busquedaCosteUniforme(String nodo_inicial, String nodo_final) {
        ArrayList<Pair<Nodo,Double>> extrae = new ArrayList<>();
        //Cola
        ArrayList<Pair<Nodo,Double>> cola = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        cola.add(new Pair<>(nodo_inicial_obj,0.0));
        Map<Nodo,Double> costo_acumulado = new HashMap<>();
        costo_acumulado.put(nodo_inicial_obj,0.0);
        while (!cola.isEmpty()) {
            //Ordenamos la cola
            Collections.sort(cola, Comparator.comparing(Pair::getValue));
            //Imprimir la cola
            for (Pair<Nodo, Double> nodo : cola) {
                System.out.print("["+nodo.getKey().getName() + " : " + nodo.getValue()+"]");
            }
            System.out.println();
            // Se extrae el nodo con menor costo acumulado de la cola.
            //Pair<Nodo, Double> nodoActual = cola.stream().min(Comparator.comparing(Pair::getValue)).get();
            Pair<Nodo, Double> nodoActual = cola.get(0);
            cola.remove(nodoActual);
            extrae.add(nodoActual);
            Nodo nodo = nodoActual.getKey();
            // Si llegamos al nodo final, se termina la búsqueda.
            if (nodo.equals(nodo_final_obj)) {
                // Hacer algo con la solución encontrada
                System.out.println("Solucion encontrada");
                for (Pair<Nodo, Double> nodo1 : extrae) {
                    System.out.print("["+nodo1.getKey().getName() + " : " + nodo1.getValue()+"] ");
                }
                return nodoActual.getValue();
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
        // Si la cola se vacía sin encontrar el nodo final, no existe solución.
        System.out.println("No existe solución");
        return null;
    }

    //Busquedas heurísticas

    //Busqueda hill climbing
    /*
     * Realiza una búsqueda hill climbing desde un nodo inicial hasta un nodo final,
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */

    public void busquedaHillClimbing(String nodo_inicial, String nodo_final) {
        ArrayList<Nodo> extrae = new ArrayList<>();
        //Cola
        ArrayList<Nodo> cola = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        cola.add(nodo_inicial_obj);
        while (!cola.isEmpty()) {
            //Ordenamos la cola de acuerdo al peso
            //cola.sort(Comparator.comparing(Pair::getValue));
            //Imprimir la cola
            for (Nodo nodo : cola) {
                System.out.print("["+nodo.getName() + ":" + nodo.getPeso()+"]");
            }
            System.out.println();
            // Se extrae el nodo con menor costo acumulado de la cola.
            //Pair<Nodo, Double> nodoActual = cola.stream().min(Comparator.comparing(Pair::getValue)).get();
            Nodo nodoActual = cola.get(0);
            cola.remove(nodoActual);
            extrae.add(nodoActual);

            // Si llegamos al nodo final, se termina la búsqueda.
            if (nodoActual.equals(nodo_final_obj)) {
                // Hacer algo con la solución encontrada
                System.out.println("Solucion encontrada");
                for (Nodo nodo : extrae) {
                    System.out.print("["+nodo.getName() + ":" + nodo.getPeso()+"]");
                }
                return;
            }
            ArrayList<Nodo> temp = new ArrayList<>();
            // Para cada vecino del nodo actual, se actualiza el costo acumulado y se añade a la cola.
            for (Edge arista : nodoActual.getWays()) {
                Nodo vecino = arista.getDestination();
                // Verificamos que el vecino no haya sido visitado y que no esté en la cola.


                if (!extrae.contains(vecino) && !cola.contains(vecino)) {
                    temp.add(vecino);
                }

            }
            //Ordenamos la cola de acuerdo al peso
            temp.sort(Comparator.comparing(Nodo::getPeso));
            //Añadimos el vecino con menor peso a la cola
            cola.add(0,temp.get(0));
            temp.remove(0);
            cola.addAll(temp);
        }
        // Si la cola se vacía sin encontrar el nodo final, no existe solución.
        System.out.println("No existe solución");
    }

    //Busqueda primero el mejor
    /*
     * Realiza una búsqueda primero el mejor desde un nodo inicial hasta un nodo final,
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */

    public void busquedaPrimeroElMejor(String nodo_inicial, String nodo_final) {
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        ArrayList<Nodo> queue = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        System.out.println("Cola: ");
        queue.add(nodo_inicial_obj);
        while (!queue.isEmpty()){
            Nodo nodo_actual = queue.get(0);
            //Imprimir la cola
            for (Nodo nodo : queue) {
                System.out.print("|"+nodo.getName() + ":"+nodo.getPeso()+"|");
            }
            System.out.println();
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
                //Ordenar la lista de hijos de acuerdo al peso
                temp.sort(Comparator.comparing(Nodo::getPeso));

                queue.addAll(0,temp);
            }
        }
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print("|"+nodo.getName() + ":"+nodo.getPeso()+"|");
        }


    }

    //Busqueda A*

    /*
     * Realiza una búsqueda A* desde un nodo inicial hasta un nodo final,
     * @param nodo_inicial El nodo desde donde se inicia la búsqueda.
     * @param nodo_final El nodo que se desea encontrar durante la búsqueda.
     */

    public void busquedaAStar(String nodo_inicial, String nodo_final) {
        //Format <Nodo, Peso+Heuristica>
        ArrayList<Pair<Nodo,Double>> nodos_visitados = new ArrayList<>();
        ArrayList<Pair<Nodo,Double>> queue = new ArrayList<>();
        //HashMap para conservar el costo acumulado de cada nodo
        HashMap<Nodo,Double> costo_acumulado = new HashMap<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        System.out.println("Cola: ");
        queue.add(new Pair<>(nodo_inicial_obj, nodo_inicial_obj.getPeso() + 0.0));
        costo_acumulado.put(nodo_inicial_obj, 0.0);
        while (!queue.isEmpty()) {
            Pair<Nodo,Double> nodo_actual = queue.get(0);
            //Imprimir la cola
            for (Pair<Nodo,Double> nodo : queue) {
                System.out.print("|" + nodo.getKey() + ":" + nodo.getValue() + "|");
            }
            System.out.println();
            queue.remove(0);
            nodos_visitados.add(nodo_actual);
            nodo_actual.getKey().setVisited(true);

            if (nodo_actual.getKey().getName().equals(nodo_final_obj.getName())) {
                break;
            }
            //Expandir el nodo

            for (Edge way : nodo_actual.getKey().getWays()) {
                Nodo vecino = way.getDestination();
                //Costo acumulado del nodo actual
                double costo_acumulado_actual = costo_acumulado.get(nodo_actual.getKey());
                //Costo acumulado del nodo vecino
                double costo_acumulado_vecino = costo_acumulado_actual + way.getDistance();
                //Costo heuristico del nodo vecino
                double costo_heuristico_vecino = vecino.getPeso();
                //Costo total del nodo vecino
                double costo_total_vecino = costo_acumulado_vecino + costo_heuristico_vecino;
                //Crear el nodo vecino con el costo total
                Pair<Nodo,Double> nodo_vecino = new Pair<>(vecino, costo_total_vecino);
                //Verificar si el nodo vecino se encuentra en la cola comprobando por la key
                boolean existe = false;


                //Buscamos si el nodo vecino ya esta en la cola
                for (Pair<Nodo,Double> nodo : queue) {
                    if (nodo.getKey().getName().equals(vecino.getName())) {
                        existe = true;
                        break;
                    }
                }
                //Verificar si el nodo vecino ya fue visitado o si ya esta en la lista de nodos a visitar
                if (!nodo_vecino.getKey().isVisited() && !existe) {
                    costo_acumulado.put(vecino, costo_acumulado_vecino);
                    queue.add(nodo_vecino);
                } else if (costo_acumulado_vecino < costo_acumulado.get(vecino)) { //Si el costo acumulado del vecino es menor al costo acumulado del nodo actual
                    costo_acumulado.put(vecino, costo_acumulado_vecino);
                    queue.remove(nodo_vecino);
                    queue.add(nodo_vecino);
                }
            }
            //Ordenar la lista de hijos de acuerdo al coste heuristico
            queue.sort(Comparator.comparing(Pair::getValue));


        }
        System.out.println("Nodos visitados: ");
        for (Pair<Nodo,Double> nodo : nodos_visitados) {
            System.out.print("|" + nodo.getKey() + ":" + nodo.getValue() + "|");
        }

    }
    /*
    public void busquedaAStar(String nodo_inicial, String nodo_final) {
        PriorityQueue<Pair<Nodo, Double>> queue = new PriorityQueue<>(Comparator.comparing(Pair::getValue));
        Set<Nodo> nodos_visitados = new HashSet<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        System.out.println("Cola: ");
        queue.add(new Pair<>(nodo_inicial_obj, nodo_inicial_obj.getPeso() + 0.0));
        nodo_inicial_obj.setCost(0.0);
        while (!queue.isEmpty()) {
            Pair<Nodo, Double> nodo_actual = queue.poll();
            //Imprimir la cola
            for (Pair<Nodo, Double> nodo : queue) {
                System.out.print("|" + nodo.getKey() + ":" + nodo.getValue() + "|");
            }
            System.out.println();
            nodos_visitados.add(nodo_actual.getKey());
            nodo_actual.getKey().setVisited(true);

            if (nodo_actual.getKey().getName().equals(nodo_final_obj.getName())) {
                break;
            }
            //Expandir el nodo
            for (Edge way : nodo_actual.getKey().getWays()) {
                Nodo vecino = way.getDestination();
                //Costo acumulado del nodo actual
                double costo_acumulado_actual = nodo_actual.getKey().getCost();
                //Costo acumulado del nodo vecino
                double costo_acumulado_vecino = costo_acumulado_actual + way.getDistance();
                //Costo heuristico del nodo vecino
                double costo_heuristico_vecino = vecino.getPeso();
                //Costo total del nodo vecino
                double costo_total_vecino = costo_acumulado_vecino + costo_heuristico_vecino;
                //Crear el nodo vecino con el costo total
                Pair<Nodo, Double> nodo_vecino = new Pair<>(vecino, costo_total_vecino);
                //Verificar si el nodo vecino ya fue visitado
                if (!vecino.isVisited() && !nodos_visitados.contains(vecino)) {
                    vecino.setCost(costo_acumulado_vecino);
                    queue.add(nodo_vecino);
                } else if (costo_acumulado_vecino < vecino.getCost()) {
                    vecino.setCost(costo_acumulado_vecino);
                    queue.removeIf(n -> n.getKey().getName().equals(vecino.getName()));
                    queue.add(nodo_vecino);
                }
            }
        }
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print("|" + nodo.getName() + ":" + nodo.getPeso() + "|");
        }
    } */

    //Greedy search
    /*
    * Realiza la busqueda greedy desde un nodo inicial hasta un nodo final
    * @param nodo_inicial Nodo inicial
    * @param nodo_final Nodo final
    *
     */
    public void busquedaGreedy(String nodo_inicial, String nodo_final) {
        ArrayList<Nodo> queue = new ArrayList<>();
        ArrayList<Nodo> nodos_visitados = new ArrayList<>();
        Nodo nodo_inicial_obj = findNode(nodo_inicial);
        Nodo nodo_final_obj = findNode(nodo_final);
        System.out.println("Cola: ");
        queue.add(nodo_inicial_obj);
        nodo_inicial_obj.setVisited(true);
        while (!queue.isEmpty()) {

            //Imprimir la cola
            for (Nodo nodo : queue) {
                System.out.print("|" + nodo.getName() + ":" + nodo.getPeso() + "|");
            }
            System.out.println();
            Nodo nodo_actual = queue.get(0);
            queue.remove(0);
            nodos_visitados.add(nodo_actual);

            assert nodo_actual != null;
            if (nodo_actual.getName().equals(nodo_final_obj.getName())) {
                break;
            }
            //Expandir el nodo
            for (Edge way : nodo_actual.getWays()) {
                //Verificar si el nodo vecino ya fue visitado
                if (!queue.contains(way.getDestination()) && !nodos_visitados.contains(way.getDestination())) {
                    queue.add(way.getDestination());
                }
            }
            //Ordenar la lista de hijos de acuerdo al coste heuristico
            queue.sort(Comparator.comparing(Nodo::getPeso));
        }
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print("|" + nodo.getName() + ":" + nodo.getPeso() + "|");
        }

    }
}
