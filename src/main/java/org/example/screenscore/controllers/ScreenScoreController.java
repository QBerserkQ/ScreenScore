package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScreenScoreController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
