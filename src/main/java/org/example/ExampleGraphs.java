package org.example;

import java.util.HashMap;

public class ExampleGraphs {

    public static Graph examplePlanar1() {
        HashMap<String, Node> x = new HashMap<>();
        Graph g = new Graph(x, 0);
        g.addNode("1", new Node("1"));
        g.addNode("2", new Node("2"));
        g.addNode("3", new Node("3"));
        g.addNode("4", new Node("4"));
        g.addNode("5", new Node("5"));
        g.addNode("6", new Node("6"));
        g.addNode("7", new Node("7"));
        g.addNode("8", new Node("8"));

        g.addEdge(1, "1", "2");
        g.addEdge(1, "1", "8");
        g.addEdge(1, "1", "6");
        g.addEdge(1, "1", "3");
        g.addEdge(1, "1", "5");
        g.addEdge(1, "2", "3");
        g.addEdge(1, "2", "8");
        g.addEdge(1, "3", "4");
        g.addEdge(1, "4", "8");
        g.addEdge(1, "4", "5");
        g.addEdge(1, "5", "6");
        g.addEdge(1, "6", "7");
        g.addEdge(1, "6", "8");
        g.addEdge(1, "7", "8");
        g.addEdge(1, "4", "6");
        return g;
    }

    public static Graph examplePlanar2() { //very simple graph
        HashMap<String, Node> x = new HashMap<>();
        Graph g = new Graph(x, 0);

        g.addNode("1", new Node("1"));
        g.addNode("2", new Node("2"));
        g.addNode("3", new Node("3"));
        g.addNode("4", new Node("4"));

        g.addEdge(1, "1", "2");
        g.addEdge(1, "2", "3");
        g.addEdge(1, "3", "4");

        return g;
    }

}
