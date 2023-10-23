package org.example;

public class Edge {

    // Edge properties
    private int weight;
    private Node fromNode;
    private Node toNode;

    // Constructor
    Edge(int weight, Node fromNode, Node toNode) {
        this.weight = weight;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setSrc(Node fromNode) {
        this.fromNode = fromNode;
    }

    public void setDest(Node toNode) {
        this.toNode = toNode;
    }

    public Node getSrc() {
        return fromNode;
    }

    public Node getDest() {
        return toNode;
    }

}
