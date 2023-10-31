package org.example;

public class Main {

    public static void main(String[] args) {

        Graph g = ExampleGraphs.examplePlanar1(); //returns a graph
        g.fiveColorPlanar();
        g.printGraph();

    }

}