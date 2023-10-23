package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Graph {

    // Graph properties
    HashMap<String, Node> nodes;
    private int numEdges;

    // Constructor
    public Graph(HashMap<String, Node> nodes, int numEdges) {
        this.nodes = nodes;
        this.numEdges = numEdges;
    }

    public boolean addNode(String name, Node node) {
        if (nodes.get(name) != null) {
            return false;
        }
        nodes.put(name, node);
        return true;
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public ArrayList<Node> getAllNodes() {
        ArrayList<Node> allNodes = new ArrayList<>();
        Iterator<Map.Entry<String, Node>> iterator = this.nodes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Node> entry = iterator.next();
            Node node = entry.getValue();
            allNodes.add(node);
        }
        return allNodes;
    }

    public boolean addEdge(Edge e) {
        if (e.getSrc().name.equalsIgnoreCase(e.getDest().name)) {
            return false;
        }
        e.getSrc().addOut(e);
        e.getDest().addIn(e);
        numEdges++;
        return true;
    }

    public boolean addEdge(int weight, String srcName, String destName) {
        if (srcName.equalsIgnoreCase(destName)) {
            return false;
        }
        Edge e = new Edge(weight, this.getNode(srcName), this.getNode(destName));
        this.addEdge(e);
        return true;
    }

    public int minDeg() {
        int min = Integer.MAX_VALUE;
        Iterator<Map.Entry<String, Node>> iterator = this.nodes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Node> entry = iterator.next();
            Node node = entry.getValue();
            if (node.deg() < min) {
                min = node.deg();
            }
        }
        return min;
    }

    public int maxDeg() {
        int max = -1;
        Iterator<Map.Entry<String, Node>> iterator = this.nodes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Node> entry = iterator.next();
            Node node = entry.getValue();
            if (node.deg() > max) {
                max = node.deg();
            }
        }
        return max;
    }

    public Edge getEdge(String srcName, String destName) {
        Node src = nodes.get(srcName);
        Node dest = nodes.get(destName);
        return this.getEdge(src, dest);
    }

    public Edge getEdge(Node src, Node dest) {
        for (Edge e : src.getOut()) {
            if (e.getDest() == dest) {
                return e;
            }
        }
        return null;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void removeEdge(String srcName, String destName) {
        this.removeEdge(this.getEdge(srcName, destName));
    }

    public void removeEdge(Node src, Node dest) {
        this.removeEdge(this.getEdge(src, dest));
    }

    public void removeEdge(Edge e) { //has to be deleted from both nodes
        e.getSrc().getOut().remove(e);
        e.getDest().getIn().remove(e);
        numEdges--;
    }

    public void removeNode(String name) {   //remove node from graph and remove all edges
        Node removed = nodes.remove(name);
        if (removed == null) {
            return;
        }
        for (Edge e : removed.getOut()) {
            this.removeEdge(e);
        }
        for (Edge e : removed.getIn()) {
            this.removeEdge(e);
        }
    }

    public boolean checkEdge(String srcName, String destName) {
        return (this.getEdge(srcName, destName) != null) ? true : false;
    }

    public boolean checkEdge(Node src, Node dest) {
        return (this.getEdge(src, dest) != null) ? true : false;
    }

    public void greedyColor(){
        //TODO
    }

    public void contract(Edge edge){
        //TODO - rufe contract(node, node) auf
    }

    public void contract(Node n1, Node n2){
        //TODO
    }

    public void removeNode(Node node) {
        this.removeNode(node.name);
    }

    // Load a graph from file
    static Graph loadFromEdgeFile(String filename) {
        int curLine = 0;
        int numEdges = 0;
        HashMap<String, Node> nodes = new HashMap<String, Node>(1024);
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = file.readLine()) != null) {
                curLine++;
                line = line.trim();
                if ((line.length() == 0) || (line.charAt(0) == ';')) continue;

                String[] token = line.split("[ \t]+");
                Node from = nodes.get(token[0]);
                if (from == null) nodes.put(token[0], from = new Node(token[0]));
                Node to = nodes.get(token[1]);
                if (to == null) nodes.put(token[1], to = new Node(token[1]));
                int weight = Integer.parseInt(token[2]);
                Edge e = new Edge(weight, from, to);
                from.addOut(e);
                to.addIn(e);
                numEdges++;
            }
            file.close();
            return new Graph(nodes, numEdges);
        } catch (Exception e) {
            System.err.println("Error in graph file (line " + curLine + "): " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    void printResults() {
        for (Node u : nodes.values()) {
            System.out.print(u.name + ":");
            Node pi = u.pi;
            System.out.print(" pi=" + (pi == null ? "null" : pi.name));
            //System.out.print(" color: " + u.color);
            System.out.print(" dist=" + u.distance);
            System.out.print(" d=" + u.dTime);
            System.out.println(" f=" + u.fTime);
        }
    }

}