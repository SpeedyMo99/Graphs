package org.example;

import org.example.Exceptions.EdgeNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Graph g = ExampleGraphs.examplePlanar2(); //returns a graph
        g.subdivide("1", "2");
        g.printEdges();

    }

}