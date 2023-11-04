package org.example.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import org.example.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends VBox {

    private List<Graph> graphs;
    private Group content;

    public GraphView() {
        this.setPadding(
                new Insets(64)
        );
        this.graphs = new ArrayList<>();
        this.content = new Group();
    }

    public GraphView(Graph graph) {
        this();
        this.graphs.add(graph);
        this.updateRendering();
    }

    public void addGraph(Graph graph) {
        this.graphs.add(graph);
        this.updateRendering();
    }

    private void updateRendering() {
        this.getChildren().removeAll(this.content);
        this.content.getChildren().clear();

        this.graphs.forEach(g -> {
            this.content.getChildren().addAll(g.elements());
        });
        this.getChildren().add(this.content);
    }
}
