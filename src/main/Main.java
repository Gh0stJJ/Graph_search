package main;

import data.Data;
import grafo.Grafo;
import grafo.Nodo;
import search.Bus_Ciegas;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Read data
        Data dt = new Data();
        Grafo graph = new Grafo();

        //<ArrayList<String>> data = dt.getData("src/data/graph_Data.csv");
        ArrayList<ArrayList<String>> data = dt.getData("src/data/Grafos/HGrafo2.csv");
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
            graph.connectNodes(nodo_or,nodo_des,distance);
        }

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
       /* Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        ArrayList<Nodo> nodos_visitados = busqueda.busquedaProfundidadIterativa("A","L");
        System.out.println("Nodos visitados: ");
        for (Nodo nodo : nodos_visitados) {
            System.out.print(nodo.getName()+" ");
        } */

        //Busqueda bidireccional

        Bus_Ciegas busqueda = new Bus_Ciegas(graph);
        busqueda.busquedaBidireccionalAnchura("A","I");
    }
}
