package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {

    // Node properties
    String name;

    @Getter
    private double x;
    @Getter
    private double y;
    private static final double RADIUS = 10;

    //enum Color {white, gray, black}

    int color;
    Node pi;
    int dTime, fTime;
    private ArrayList<Edge> out;
    private ArrayList<Edge> in;

    // Search status
    int distance;
    Node predecessor;
    int timesVisited;
    int backDistance;
    Node backPredecessor;

    // Constructor
    public Node(String name) {
        this.name = name;
        this.color = 0; //white
        out = new ArrayList<>(64);
        in = new ArrayList<>(64);

        this.x = this.randInRange(0, 720);
        this.y = this.randInRange(0, 400);
    }

    private double randInRange(double start, double end) {
        Random random = new Random();
        double range = end - start;
        double scatter = random.nextDouble();
        double rand = start + range * scatter;
        return rand;
    }

    void addOut(Edge e) {
        out.add(e);
    }

    void addIn(Edge e) {
        in.add(e);
    }

    public ArrayList<Edge> getOut() {
        return out;
    }

    public ArrayList<Edge> getIn() {
        return in;
    }

    public ArrayList<Node> getNeighbors() {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (Edge e : this.out) {
            Node n = e.getDest();
            if (!neighbors.contains(n)) {
                neighbors.add(n);
            }
        }
        for (Edge e : this.in) {
            Node n = e.getSrc();
            if (!neighbors.contains(n)) {
                neighbors.add(n);
            }
        }
        return neighbors;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int deg() {
        return this.getNeighbors().size();
    }

    public Circle element() {
        Circle circle = new Circle();
        circle.setFill(Color.BLUE);
        circle.setRadius(RADIUS);
        circle.setTranslateX(this.x);
        circle.setTranslateY(this.y);
        return circle;
    }

    public String toString() {
        return name + ": color: " + color;
    }

}
