package main;

import grafo.Edge;
import grafo.Grafo;
import grafo.Nodo;
import grafo.Pair;

import java.util.Scanner;
import java.io.IOException;

public class Launcher {

        public static void main(String[] args) throws IOException, InterruptedException {
            System.out.println("Generador de grafos");
            Grafo graph = new Grafo();
            Scanner sc = new Scanner(System.in);
            do {
                //Clear screen
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();;
                System.out.println("Menu");
                System.out.println("1. Agregar nodo");
                System.out.println("2. Conectar nodos");
                System.out.println("3. Mostar nodos");
                System.out.println("4. Salir");
                System.out.print("Opcion: ");
                String option = sc.nextLine();
                switch (option) {
                    //Add node
                    case "1" -> {

                        System.out.println("Nombre del nodo: ");
                        String name = sc.nextLine();
                        System.out.println("ID del nodo: ");
                        String id = sc.nextLine();
                        Nodo nodo = new Nodo(name);
                        graph.addNode(nodo);
                        System.out.println("Nodo agregado \n \n \n");
                    }
                    //Connect nodes
                    case "2" -> {

                        System.out.println("Nodos disponibles: ");
                        graph.printNodes();
                        System.out.println("Nombre del nodo origen?: ");
                        String name1 = sc.nextLine();
                        System.out.println("Nombre del nodo destino?: ");
                        String name2 = sc.nextLine();
                        System.out.println("Distancia entre los nodos?: ");
                        String distance = sc.nextLine();
                        //Find nodes
                        Nodo node1 = null;
                        Nodo node2 = null;
                        for (Nodo node : graph.getNodes()) {
                            if (node.getName().equals(name1)) {
                                node1 = node;
                            }
                            if (node.getName().equals(name2)) {
                                node2 = node;
                            }
                        }
                        //Connect nodes
                        if (node1 != null && node2 != null){
                            node1.addWay(node2, Double.parseDouble(distance));
                        }


                        System.out.println("Nodos conectados \n \n \n");
                    }
                    case "3" -> {
                        System.out.println("Nodos disponibles: ");
                        //Print each connexion
                        for (Nodo node : graph.getNodes()) {
                            System.out.println("Nodo: " + node.getName());
                            System.out.println("ID: " + node.getId());
                            System.out.println("Caminos: ");
                            for (Edge way : node.getWays()) {
                                System.out.println("Nombre: " + way.getDestination() + "Distancia: " + way.getDistance());
                            }
                        }
                    }

                    case "4" -> {
                        System.out.println("Saliendo...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opcion invalida");
                }
            } while (true);
        }
}
