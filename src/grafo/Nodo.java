package grafo;

import java.util.ArrayList;

//Graph node
public class Nodo implements Comparable<Nodo> {
    private final String name;
    private String id;
    private ArrayList<Edge> ways;
    private int peso;
    private double cost;

    private boolean visited = false;

    public Nodo(String name) {
        this.name = name;
        //this.id = id;
        ways = new ArrayList<Edge>();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void addWay(Nodo dest_node, double distance){
        ways.add(new Edge(this, dest_node, distance));
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Edge> getWays() {
        return ways;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public int getPeso(){
        return peso;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    @Override
    public int compareTo(Nodo o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
