package org.example;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Node> x = new HashMap<>();
        Graph g = new Graph(x, 0);
        g.addNode("1", new Node("1"));
        g.addNode("2", new Node("2"));
        g.addNode("3", new Node("3"));
        g.addNode("4", new Node("4"));
        g.addNode("5", new Node("5"));
        g.addNode("6", new Node("6"));
        g.addEdge(1, "1", "3");
        g.addEdge(1, "1", "2");
        g.addEdge(1, "1", "5");
        g.addEdge(1, "2", "3");
        g.addEdge(1, "2", "4");
        g.addEdge(1, "3", "4");
        g.addEdge(1, "4", "1");
        g.addEdge(1, "1", "6");

        System.out.println(g.minDeg());
        System.out.println(g.maxDeg());


       /* Edge e = new Edge(1, g.getNode("hallo"), g.getNode("bla"));
        g.addEdge(e);
        if(g.getEdge("hallo", "bla") != null){
            System.out.println("Noch ists da");
        }
        System.out.println(g.getNumEdges());
        g.removeEdge(g.getNode("hallo"), g.getNode("bla"));
        if(g.getEdge("hallo", "bla") == null){
            System.out.println("Jetzt weg");
        }
        System.out.println(g.getNumEdges());

        */

    }

}