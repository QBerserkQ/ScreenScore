package org.example.screenscore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenScoreApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ScreenScoreApplication.class.getResource("ScreenScore-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("style/style.css").toExternalForm());
        stage.setTitle("Screen Score");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
