package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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

    @SuppressWarnings("unused")
    public int getWeight() {
        return weight;
    }

    @SuppressWarnings("unused")
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @SuppressWarnings("unused")
    public void setSrc(Node fromNode) {
        this.fromNode = fromNode;
    }

    @SuppressWarnings("unused")
    public void setDest(Node toNode) {
        this.toNode = toNode;
    }

    public Node getSrc() {
        return fromNode;
    }

    public Node getDest() {
        return toNode;
    }

    public Line element() {
        double x1 = this.fromNode.getX();
        double y1 = this.fromNode.getY();
        double x2 = this.toNode.getX();
        double y2 = this.toNode.getY();

        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(4);
        return line;
    }

    public String toString(){
        //String s = "Edge: Gewicht: " + weight + " From: " + this.fromNode.toString() + " To: " + this.toNode.toString();
        return this.fromNode.name + "--" + this.toNode.name + "|| ";
    }

    public String detailed(){
        return "Edge: Gewicht: " + weight + " From: " + this.fromNode.toString() + " To: " + this.toNode.toString();
    }

}
