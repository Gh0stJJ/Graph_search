package main;

import data.Data;
import grafo.Grafo;
import grafo.Nodo;
import grafo.Pair;
import search.Bus_Ciegas;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Read data
        Data dt = new Data();
        Grafo graph = new Grafo();
        ArrayList<Pair<String,Double>> search_times = new ArrayList<>();
        ArrayList<Pair<String,Double>> comp_cost = new ArrayList<>();

        //jfilechooser
        ArrayList<ArrayList<String>> data = dt.getData("src/data/Grafos/HGrafo2.csv");
        //List of weights
        Data dtw = new Data();
        ArrayList<ArrayList<String>> weights = dtw.getData("src/data/Grafos/HGrafo2_Pesos.csv");
        //Remove header

        //Add nodes

        for (ArrayList<String> row : data) {
            Nodo nodo_or = new Nodo(row.get(0));
            Nodo nodo_des = new Nodo(row.get(1));
            graph.addNode(nodo_or);
            graph.addNode(nodo_des);

        }
        //Add ways
        // min distance
        double epsilon = Double.MAX_VALUE;
        for (ArrayList<String> row : data) {
            Nodo nodo_or = graph.findNode(row.get(0));
            Nodo nodo_des = graph.findNode(row.get(1));
            double distance = Double.parseDouble(row.get(2));
            if (distance < epsilon){
                epsilon = distance;
            }
            //double distance = 1;
            graph.connectNodes(nodo_or,nodo_des,distance);
        }
        //Add weights
        for (ArrayList<String> row : weights) {
            int peso = Integer.parseInt(row.get(1));
            graph.addWeight(row.get(0),peso);
        }

        //Graph properties
        float num_nodes = graph.getNodes().size();
        float num_edges = graph.getEdges().size();
        //número promedio de sucesores que tiene un nodo en el árbol de búsqueda.
        double b= num_edges/num_nodes;
        //profundidad máxima del árbol de búsqueda
        double dmax = Math.ceil(Math.log(num_nodes)/Math.log(b));

        System.out.println("Profundidad maxima: "+dmax);
        System.out.println("Numero de nodos: "+num_nodes);
        System.out.println("Numero de aristas: "+num_edges);
        System.out.println("Factor de ramificacion: "+b);

        //Menu
        System.out.println("Menu");

        String sel = " ";

        do{
            System.out.println();
            System.out.println("1. Busqueda a ciegas anchura");
            System.out.println("2. Busqueda a ciegas profundidad");
            System.out.println("3. Busqueda a ciegas profundidad iterativa");
            System.out.println("4. Busqueda bidireccional anchura");
            System.out.println("5. Busqueda bidireccional profundidad");
            System.out.println("6. Busqueda de costo uniforme");
            System.out.println("-------------Busquedas Heuristicas----------------");
            System.out.println("7. Busqueda Hill Climbing");
            System.out.println("8. Busqueda Primero el mejor");
            System.out.println("9. Busqueda A*");
            System.out.println("10. Busqueda Avara");
            System.out.println("11. Resumen de busquedas");
            System.out.println("12. Ejecutar todas las busquedas");
            System.out.println("_. Salir");
            Scanner sc = new Scanner(System.in);
            sel= sc.nextLine();


            switch (sel) {
                case "1" -> {
                    System.out.println("Busqueda a ciegas anchura");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino = sc.next();
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaAnchura(nodo_origen, nodo_destino);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Anchura",timeElapsed*Math.pow(10,-9)));
                    int d = graph.getDepth(nodo_origen,nodo_destino);
                    //Complejidad computacional y tiempo de busqueda busqueda anchura
                    double complex = (Math.pow(b,d));
                    comp_cost.add(new Pair<>("Busqueda Anchura",complex));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^d): " + b + "^" + d);
                    graph.flush();
                }
                case "2" -> {
                    System.out.println("Busqueda a ciegas profundidad");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen2 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino2 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaProfundidad(nodo_origen2, nodo_destino2);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Profundidad",timeElapsed*Math.pow(10,-9)));
                    //Complejidad computacional y tiempo de busqueda busqueda profundidad
                    //Profundidad maxima del arbol

                    double complex = (Math.pow(b,dmax));
                    comp_cost.add(new Pair<>("Busqueda Profundidad",complex));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^m): " + b + "^" + dmax);
                    graph.flush();
                }
                case "3" -> {
                    System.out.println("Busqueda a ciegas profundidad iterativa");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen3 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino3 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    int d = graph.busquedaProfundidadIterativa(nodo_origen3, nodo_destino3);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Profundidad Iterativa",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda profundidad iterativa
                    double complex = (Math.pow(b,d));
                    comp_cost.add(new Pair<>("Busqueda Profundidad Iterativa",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^d): " + b + "^" + d);
                    graph.flush();
                }
                case "4" -> {
                    System.out.println("Busqueda bidireccional anchura");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen4 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino4 = sc.next();
                    int d = graph.getDepth(nodo_origen4,nodo_destino4);
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaBidireccionalAnchura(nodo_origen4, nodo_destino4);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Bidireccional Anchura",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda bidireccional anchura
                    double complex = (Math.pow(b,(d/2.0)));
                    comp_cost.add(new Pair<>("Busqueda Bidireccional Anchura",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^(d/2)): " + b + "^" + d+"/2");
                    graph.flush();
                }
                case "5" -> {
                    System.out.println("Busqueda bidireccional profundidad");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen5 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino5 = sc.next();
                    int d = graph.getDepth(nodo_origen5,nodo_destino5);
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaBidireccionalProfundidad(nodo_origen5, nodo_destino5);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Bidireccional Profundidad",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda bidireccional profundidad
                    double complex = (Math.pow(b,(d/2.0)));
                    comp_cost.add(new Pair<>("Busqueda Bidireccional Profundidad",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^(d/2)): " + b + "^" + d+"/2");
                    graph.flush();
                }
                case "6" -> {
                    System.out.println("Busqueda de costo uniforme");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen6 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino6 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    Double c = graph.busquedaCosteUniforme(nodo_origen6, nodo_destino6);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Coste Uniforme",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda de costo uniforme
                    double complex = (Math.pow(b,(c/epsilon)));
                    comp_cost.add(new Pair<>("Busqueda Coste Uniforme",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^(c/epsilon)): " + b + "^(" + c+"/" + epsilon+ ")");
                    graph.flush();
                }
                case "7" -> {
                    System.out.println("Busqueda Hill Climbing");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen7 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino7 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    int k =graph.busquedaHillClimbing(nodo_origen7, nodo_destino7);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Hill Climbing",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda hill climbing
                    double complex = (num_nodes*k);
                    comp_cost.add(new Pair<>("Busqueda Hill Climbing",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(n*k): " + num_nodes + "*" + k);
                    graph.flush();
                }
                case "8" -> {
                    System.out.println("Busqueda Primero el mejor");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen8 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino8 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaPrimeroElMejor(nodo_origen8, nodo_destino8);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Primero el mejor",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda primero el mejor
                    int d = graph.getDepth(nodo_origen8,nodo_destino8);
                    double complex = (Math.pow(b,d));
                    comp_cost.add(new Pair<>("Busqueda Primero el mejor",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^h): " + b + "^" + d);

                    graph.flush();
                }
                case "9" -> {
                    System.out.println("Busqueda A*");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen9 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino9 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    graph.busquedaAStar(nodo_origen9, nodo_destino9);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda A*",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda A*
                    int d = graph.getDepth(nodo_origen9,nodo_destino9);
                    double complex = (Math.pow(b,d));
                    comp_cost.add(new Pair<>("Busqueda A*",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^d): " + b + "^" + d);
                    graph.flush();
                }
                case "10" -> {
                    System.out.println("Busqueda Avara");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen10 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino10 = sc.next();
                    //time start
                    long start = System.nanoTime();
                    int k = graph.busquedaGreedy(nodo_origen10, nodo_destino10);
                    //time end
                    long end = System.nanoTime();
                    //time elapsed
                    long timeElapsed = end - start;
                    search_times.add(new Pair<>("Busqueda Avara",timeElapsed*Math.pow(10,-9)));
                    System.out.println("\n Tiempo de busqueda: "+timeElapsed*Math.pow(10,-9) + " segundos");
                    //Complejidad computacional busqueda Avara
                    double m = Math.ceil(num_nodes/k);
                    double complex = (Math.pow(b,m));
                    comp_cost.add(new Pair<>("Busqueda Avara",complex));
                    System.out.println("\n Complejidad computacional: "+complex + " O(b^m): " + b + "^" + m);
                    graph.flush();
                }
                case "." -> System.out.println("Salir");
            }

        }while (!sel.equals("."));





        //Print nodes
        //graph.printNodes();

        //Search
        //Busqueda a ciegas anchura

        /*Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        ArrayList<Nodo> nodos_visitados = busqueda.busquedaAnchura("A","J");
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.println(nodo.getName());
        } */

        //Busqueda a ciegas profundidad
        /*Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        ArrayList<Nodo> nodos_visitados = busqueda.busquedaProfundidad("A","L");
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.println(nodo.getName());
        } */

        //Busqueda a ciegas profundidad iterativa
        /*Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        ArrayList<Nodo> nodos_visitados = busqueda.busquedaProfundidadIterativa("A","L");
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print(nodo.getName()+" ");
        } */

        //Busqueda bidireccional

        //Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        //busqueda.busquedaBidireccionalAnchura("1","16");

        //Busqueda coste uniforme

       // Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        //busqueda.busquedaCosteUniforme("A","F");

    }
}
