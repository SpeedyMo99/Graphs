package org.example;

public class Main {

    public static void main(String[] args) {

        Graph g = ExampleGraphs.examplePlanar2(); //returns a graph
        g.greedyColor();
        g.printGraph();

    }

}