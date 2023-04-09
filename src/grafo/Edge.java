package grafo;

public class Edge {
    private Nodo source;
    private Nodo destination;
    private double distance;

    public Edge(Nodo source, Nodo destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Nodo getSource() {
        return source;
    }

    public Nodo getDestination() {
        return destination;
    }

    public double getDistance() {
        return distance;
    }

    // Opcional: implementar un método toString para imprimir la información de la arista
    @Override
    public String toString() {
        return String.format("%s -> %s (distancia: %.2f)", source.getName(), destination.getName(), distance);
    }

}