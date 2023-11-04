module Graphs {
    requires lombok;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.controls;

    opens org.example.ui to javafx.graphics, javafx.controls;
    exports org.example.ui;
}
