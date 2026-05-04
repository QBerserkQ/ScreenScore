package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.services.ReviewService;

import java.util.Iterator;
import java.util.List;

public class ScreenScoreController {
    @FXML
    private SideBarController sidebarController;

    @FXML
    private MainController mainController;

    private static final ReviewService reviewService = new ReviewService();

    @FXML
    public void initialize() {
        mainController.setReviewService(reviewService);
        List<ReviewClass> list = reviewService.getAllReviews();

        for (ReviewClass review : list) {
            mainController.addReviewCard(review);
        }

        sidebarController.setOnReviewCreated(review -> {
            ReviewClass rw = reviewService.addReview(review);
            mainController.addReviewCard(rw);
        });

        sidebarController.setOnReviewType(t -> {
                    mainController.clearReviewCard();
                    List<ReviewClass> list1 = (t == null) ?
                            reviewService.getAllReviews()
                            : reviewService.getReviewsByType(t);
                    Iterator<ReviewClass> iterator1 = list1.iterator();
                    while (iterator1.hasNext()) {
                        ReviewClass rw1 = iterator1.next();
                        mainController.addReviewCard(rw1);
                    }
                }
        );
    }
}
