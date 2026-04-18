package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScreenScoreController {
    @FXML
    private SideBarController sidebarController;

    @FXML
    private MainController mainController;

    @FXML
    public void initialize() {
        sidebarController.setOnReviewCreated(review -> {
            mainController.addReviewCard(review);
        });
    }
}
