package grafo;

import java.util.ArrayList;

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


    public ArrayList<Nodo> getNodes() {
        return nodes;
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
}
