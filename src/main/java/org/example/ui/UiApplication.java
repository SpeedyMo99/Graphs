package org.example.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ExampleGraphs;
import org.example.Graph;

public class UiApplication extends Application {

    private Scene scene;
    private VBox root;

    @Override
    public void start(Stage stage) throws Exception {
        this.root = new VBox();
        this.scene = new Scene(this.root, 900, 640);

        Graph g = ExampleGraphs.examplePlanar1();
        GraphView view = new GraphView(g);

        this.root.getChildren().add(view);

        stage.setScene(this.scene);
        stage.show();
    }
}
