package main;

import data.Data;
import grafo.Grafo;
import grafo.Nodo;
import search.Bus_Ciegas;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Read data
        Data dt = new Data();
        Grafo graph = new Grafo();

        //<ArrayList<String>> data = dt.getData("src/data/graph_Data.csv");
        ArrayList<ArrayList<String>> data = dt.getData("src/data/Grafos/test.csv");
        //List of weights
        Data dtw = new Data();
        ArrayList<ArrayList<String>> weights = dtw.getData("src/data/Grafos/Test_weights.csv");
        //Remove header
        //data.remove(0);
        //Add nodes

        for (ArrayList<String> row : data) {
            Nodo nodo_or = new Nodo(row.get(0));
            Nodo nodo_des = new Nodo(row.get(1));
            graph.addNode(nodo_or);
            graph.addNode(nodo_des);

        }
        //Add ways
        for (ArrayList<String> row : data) {
            Nodo nodo_or = graph.findNode(row.get(0));
            Nodo nodo_des = graph.findNode(row.get(1));
            double distance = Double.parseDouble(row.get(2));
            //double distance = 1;
            graph.connectNodes(nodo_or,nodo_des,distance);
        }
        //Add weights
        for (ArrayList<String> row : weights) {
            int peso = Integer.parseInt(row.get(1));
            graph.addWeight(row.get(0),peso);
        }



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
            System.out.println("_. Salir");
            Scanner sc = new Scanner(System.in);
            sel= sc.nextLine();

            switch (sel){
                case "1":
                    System.out.println("Busqueda a ciegas anchura");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino = sc.next();

                    graph.busquedaAnchura(nodo_origen,nodo_destino);

                    break;
                case "2":
                    System.out.println("Busqueda a ciegas profundidad");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen2 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino2 = sc.next();

                    graph.busquedaProfundidad(nodo_origen2,nodo_destino2);
                    break;


                case "3":
                    System.out.println("Busqueda a ciegas profundidad iterativa");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen3 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino3 = sc.next();

                    graph.busquedaProfundidadIterativa(nodo_origen3,nodo_destino3);
                    break;

                case "4":
                    System.out.println("Busqueda bidireccional anchura");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen4 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino4 = sc.next();

                    graph.busquedaBidireccionalAnchura(nodo_origen4,nodo_destino4);
                    break;


                case "5":
                    System.out.println("Busqueda bidireccional profundidad");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen5 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino5 = sc.next();

                    graph.busquedaBidireccionalProfundidad(nodo_origen5,nodo_destino5);
                    break;

                case "6":
                    System.out.println("Busqueda de costo uniforme");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen6 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino6 = sc.next();

                    graph.busquedaCosteUniforme(nodo_origen6,nodo_destino6);
                    break;

                case "7":
                    System.out.println("Busqueda Hill Climbing");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen7 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino7 = sc.next();

                    graph.busquedaHillClimbing(nodo_origen7,nodo_destino7);
                    break;

                case "8":
                    System.out.println("Busqueda Primero el mejor");
                    System.out.println("Ingrese el nodo origen");
                    String nodo_origen8 = sc.next();
                    System.out.println("Ingrese el nodo destino");
                    String nodo_destino8 = sc.next();

                    graph.busquedaPrimeroElMejor(nodo_origen8,nodo_destino8);
                    break;

                case ".":
                    System.out.println("Salir");
                    break;
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
