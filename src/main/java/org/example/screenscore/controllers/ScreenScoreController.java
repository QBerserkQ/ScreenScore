package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.services.ReviewService;

import java.util.List;

public class ScreenScoreController {
    @FXML
    private SideBarController sidebarController;

    @FXML
    private MainController mainController;

    private static final ReviewService reviewService = new ReviewService();

    @FXML
    public void initialize() {
        List<ReviewClass> list = reviewService.getAllReviews();

        for (ReviewClass review : list) {
            mainController.addReviewCard(review);
        }

        sidebarController.setOnReviewCreated(review -> {
            ReviewClass rw = reviewService.addReview(review);
            mainController.addReviewCard(rw);
        });
    }
}
