package org.example;

import org.example.Exceptions.EdgeNotFoundException;
import org.example.Exceptions.NodeNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
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
        for (Node node : this.nodes.values()) {
            allNodes.add(node);
        }
        return allNodes;
    }

    public boolean addEdge(Edge e) {
        if (e.getSrc().name.equalsIgnoreCase(e.getDest().name)) {
            return false;   //schlingen verboten
        }
        if (this.checkEdge(e.getSrc(), e.getDest())) {
            return false;
        }
        e.getSrc().addOut(e);
        e.getDest().addIn(e);
        numEdges++;
        return true;
    }

    public boolean addEdge(int weight, Node src, Node dest) {
        if (src.name.equalsIgnoreCase(dest.name)) {
            return false;
        }
        Edge e = new Edge(weight, src, dest);
        return this.addEdge(e);
    }

    public boolean addEdge(int weight, String srcName, String destName) {
        if (srcName.equalsIgnoreCase(destName)) {
            return false;
        }
        Edge e = new Edge(weight, this.getNode(srcName), this.getNode(destName));
        return this.addEdge(e);
    }

    public int minDeg() {
        int min = Integer.MAX_VALUE;
        for (Node node : this.nodes.values()) {
            if (node.deg() < min) {
                min = node.deg();
            }
        }
        return min;
    }

    public int maxDeg() {
        int max = -1;
        for (Node node : this.nodes.values()) {
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
        Edge e = this.getEdge(srcName, destName);
        if (e == null) {
            throw new EdgeNotFoundException();
        }
        this.removeEdge(e);
    }

    public void removeEdge(Node src, Node dest) {
        Edge e = this.getEdge(src, dest);
        if (e == null) {
            throw new EdgeNotFoundException();
        }
        this.removeEdge(e);
    }

    public void removeEdge(Edge e) { //has to be deleted from both nodes
        e.getSrc().getOut().remove(e);
        e.getDest().getIn().remove(e);
        numEdges--;
    }

    public void removeNode(String name) {   //remove node from graph and remove all edges
        Node removed = this.nodes.remove(name);
        if (removed == null) {
            throw new NodeNotFoundException();
        } //knoten ist weg, lösche nun ausgehende und eingehende kanten
        for (int i = 0; i < removed.getOut().size(); i++) {
            this.removeEdge(removed.getOut().get(i));   //TODO after contracting, the number of edges isn't correct anymore
        }
        for (int i = 0; i < removed.getIn().size(); i++) {
            this.removeEdge(removed.getIn().get(i));
        }
    }

    public boolean checkEdge(String srcName, String destName) {
        return (this.getEdge(srcName, destName) != null) ? true : false;
    }

    public boolean checkEdge(Node src, Node dest) {
        return (this.getEdge(src, dest) != null) ? true : false;
    }

    public void greedyColor() {
        ArrayList<Node> nodes = this.getAllNodes();
        nodes.get(0).setColor(1);
        ArrayList<Integer> neighborColors = new ArrayList<>();
        for (int i = 1; i < nodes.size(); i++) {
            if (neighborColors.size() != 0) {
                neighborColors.clear();
            }
            Node currentNode = nodes.get(i);
            for (Node neighbor : currentNode.getNeighbors()) {    //Liste enthält alle farben der nachbarn von node_i
                neighborColors.add(neighbor.getColor());
            }
            int c = 1;
            while (neighborColors.contains((int) c)) {
                c++;
            }
            currentNode.setColor(c); //c(node_i) = kleinste farbe, die nicht in Fi ist
        }
    }

    public void fiveColorPlanar() {  //mostly assumes that graph is planar -- checking if graph is planar should be done somewhere else
        ArrayList<Node> allNodes = this.getAllNodes();

        if (allNodes.size() <= 5) {   //if graph has only 5 nodes, color them and return
            this.greedyColor();     //since we only have 5 nodes (at most), greedy color will use at most 5 colors
            return;
        }

        Stack<ArrayList<Node>> removedNodesStack = new Stack<>();
        Stack<ArrayList<Edge>> removedEdgesStack = new Stack<>();
        Stack<ArrayList<Edge>> addedEdgeStack = new Stack<>();
        Stack<String> fusedNodesStack = new Stack<>();

        for (Node u : this.getAllNodes()) {     //iteriere durch alle knoten
            if (!this.getAllNodes().contains(u)) {
                continue;
            }
            if (this.getAllNodes().size() <= 5) {
                break;
            }
            ArrayList<Node> removedNodeList = new ArrayList<>();
            ArrayList<Edge> removedEdgesList = new ArrayList<>();
            ArrayList<Edge> addedEdgesList = new ArrayList<>();

            if (u.deg() <= 4) {                 //fall 1: deg <= 4
                removedNodeList.add(u);         //speichere u in liste
                for (Edge e : u.getIn()) {
                    removedEdgesList.add(e);       //speichere jede kante von u in liste
                }
                for (Edge e : u.getOut()) {
                    removedEdgesList.add(e);        //speichere jede kante von u in liste
                }
                removedNodesStack.push(removedNodeList);        //speichere die 3 listen im jeweiligen stack
                removedEdgesStack.push(removedEdgesList);
                addedEdgeStack.push(addedEdgesList);    //leere liste
                fusedNodesStack.push("");   //leerer String
                this.removeNode(u);             //entferne u (zusammen mit den kanten)
            } else if (u.deg() == 5) {  //falls 2: deg = 5

                ArrayList<Node> neighborsU = u.getNeighbors();
                Node v = null;
                Node w = null;
                outerloop:
                for (int i = 0; i < neighborsU.size(); i++) {       //finde 2 knoten v,w in der nachbarschaft, zwischen denen keine kante existiert
                    for (int j = 0; j < neighborsU.size(); j++) {
                        if (i != j && !this.checkEdge(neighborsU.get(i), neighborsU.get(j))) {
                            v = neighborsU.get(i);
                            w = neighborsU.get(j);
                            break outerloop;
                        }
                    }
                }
                if (v == null || w == null) {
                    throw new IllegalArgumentException("mhhh");
                }
                removedNodeList.add(v);     //speichere v,w in liste (diese knoten werden entfernt)
                removedNodeList.add(w);
                for (Node node : removedNodeList) {   //for v, w
                    for (Edge e : node.getIn()) {
                        removedEdgesList.add(e);       //speichere jede kante von v,w in liste
                    }
                    for (Edge e : node.getOut()) {
                        removedEdgesList.add(e);
                    }
                    ArrayList<Node> neighborsVW = node.getNeighbors();
                    for (Node neighborVW : neighborsVW) {
                        if (!(this.checkEdge(u, neighborVW) || this.checkEdge(neighborVW, u))) {    //speichere kanten, die beim kontrahieren hinzugefügt werden in liste
                            addedEdgesList.add(new Edge(1, u, neighborVW));
                        }
                    }
                }
                fusedNodesStack.push(u.name);
                removedNodesStack.push(removedNodeList);
                removedEdgesStack.push(removedEdgesList);   //pushing the lists to appropriate stack
                addedEdgeStack.push(addedEdgesList);
                this.contract(u, v);
                this.contract(u, w);
            }
        }
        //only at most 5 nodes left, which can be colored with <=5 colors
        this.greedyColor();

        while (removedNodesStack.size() > 0) {
            ArrayList<Node> removedNodesList = removedNodesStack.pop();     //getting nodes, that got removed
            ArrayList<Edge> removedEdgesList = removedEdgesStack.pop();
            ArrayList<Edge> addedEdgesList = addedEdgeStack.pop();
            String fusedNodeName = fusedNodesStack.pop();
            ArrayList<Integer> neighborColors = new ArrayList<>();

            for (Node nodeToAdd : removedNodesList) {
                this.addNode(nodeToAdd.name, nodeToAdd);
            }
            for (Edge edgeToAdd : removedEdgesList) {
                this.addEdge(edgeToAdd);
            }
            for (Edge edgeToRem : addedEdgesList) {
                if (!(edgeToRem.getSrc().name.equalsIgnoreCase(edgeToRem.getDest().name))) {
                    Edge rem = this.getEdge(edgeToRem.getSrc(), edgeToRem.getDest());
                    this.removeEdge(rem);
                }
            }

            if (removedNodesList.size() == 1) {
                Node node = this.getNode(removedNodesList.get(0).name); //if only one node, then it was case 1 (deg <= 4) and thus we can add & color the node directly
                for (Node neighbor : node.getNeighbors()) {
                    neighborColors.add(neighbor.getColor());
                }
                int c = 1;
                while (neighborColors.contains((int) c)) {
                    c++;
                }
                node.setColor(c);
            } else if (removedNodesList.size() == 2) {
                Node u = this.getNode(fusedNodeName);
                for (Node node : removedNodesList) {
                    node.setColor(u.getColor());
                }
                for (Node neighbor : u.getNeighbors()) {
                    neighborColors.add(neighbor.getColor());
                }
                int c = 1;
                while (neighborColors.contains((int) c)) {
                    c++;
                }
                u.setColor(c);
            }

        }
    }

    public void fiveColorPlanar2() {
        ArrayList<Node> nodes = this.getAllNodes();
        nodes.get(0).setColor(1);       //2
        for (int i = 2; i < nodes.size(); i++) {    //3
            Node currentNode = nodes.get(i);
            ArrayList<Node> neighbors = currentNode.getNeighbors();
            if (neighbors.size() <= 4) {  //4
                ArrayList<Integer> neighborColors = new ArrayList<>();  //5
                for (Node neighbor : neighbors) {
                    neighborColors.add(neighbor.getColor());    //5
                }
                Integer newColor = 1;   //6
                while (neighborColors.contains((int) newColor)) {
                    newColor++;
                }
                currentNode.setColor(newColor); //6
            } else {
                ArrayList<Integer> neighborColors = new ArrayList<>();  //8
                for (Node neighbor : neighbors) {
                    int c = neighbor.getColor();
                    if (!neighborColors.contains((int) c)) {  //Menge beinhaltet nur verschiedene farben (damit in zeile 9 die Auswertung der Länge der Liste Sinn macht)
                        neighborColors.add(c);
                    }
                }   //8
                if (neighborColors.size() == neighbors.size()) {  //9
                    Node x = null, y = null;   //10
                    outerloop:
                    for (int i1 = 0; i < neighbors.size(); i1++) {       //finde 2 knoten v,w in der nachbarschaft, zwischen denen keine kante existiert
                        for (int j = 0; j < neighbors.size(); j++) {
                            if (i1 != j && !this.checkEdge(neighbors.get(i1), neighbors.get(j))) {
                                x = neighbors.get(i1);
                                y = neighbors.get(j);
                                break outerloop;
                            }
                        }
                    }   //10
                    currentNode.setColor(x.getColor()); //12
                    x.setColor(y.getColor());   //11
                } else {  //13
                    Integer newColor = 1;   //14
                    while (neighborColors.contains((int) newColor)) {
                        newColor++;
                    }
                    currentNode.setColor(newColor); //14
                }
            }
        }
    }

    public void contract(Edge edge) {
        this.contract(edge.getSrc(), edge.getDest());
    }

    public void contract(Node u, Node v) {
        if (!this.checkEdge(u, v)) {
            throw new EdgeNotFoundException();
        }
        ArrayList<Node> neighborsV = v.getNeighbors(); //liste aller nachbarn von v
        this.removeEdge(u, v);
        this.removeNode(v);
        for (Node neighbor : neighborsV) {
            if (!(this.checkEdge(u, neighbor) || this.checkEdge(neighbor, u))) {
                this.addEdge(1, u, neighbor);
            }
        }
    }

    public void contract(String nameU, String nameV) {
        this.contract(this.nodes.get(nameU), this.nodes.get(nameV));
    }

    public void subdivide(Edge e) {
        //TODO: implement this method together with subdivide(Node u, Node v) and subdivide(String nameU, String nameV)
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

    void printGraph() {
        System.out.println("Number of Edges: " + this.numEdges);
        for (Node u : this.nodes.values()) {
            for (Edge e : u.getOut()) {
                System.out.println(e.detailed());
            }
        }
    }

    void printEdges() {
        for (Node u : this.getAllNodes()) {
            for (Edge e : u.getOut()) {
                System.out.print(e);
            }
            System.out.println("");
        }
    }

}