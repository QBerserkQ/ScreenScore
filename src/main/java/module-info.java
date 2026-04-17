module org.example.screenscore {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.screenscore to javafx.fxml;
    exports org.example.screenscore;
    exports org.example.screenscore.controllers;
    opens org.example.screenscore.controllers to javafx.fxml;
}