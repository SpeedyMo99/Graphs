package org.example;

import java.util.ArrayList;

public class Node {

    // Node properties
    String name;

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

    public String toString() {
        return name + ": color: " + color;
    }

}
