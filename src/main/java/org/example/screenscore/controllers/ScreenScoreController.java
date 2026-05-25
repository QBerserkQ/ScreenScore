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
    private DetailController detailController;

    @FXML
    private MainController mainController;

    private static final ReviewService reviewService = new ReviewService();

    @FXML
    public void initialize() {
        mainController.setReviewService(reviewService);
        List<ReviewClass> list = reviewService.getAllReviews();

        mainController.setListReviews(list);
        detailController.updateStats(list);

        sidebarController.setOnReviewCreated(review -> {
            ReviewClass rw = reviewService.addReview(review);
            list.add(rw);
            mainController.addNewReview(rw);
            detailController.updateStats(list);
        });

        sidebarController.setOnReviewType(t -> {
                    List<ReviewClass> list1 = (t == null) ?
                            reviewService.getAllReviews()
                            : reviewService.getReviewsByType(t);
                    mainController.setListReviews(list1);
                }
        );

        mainController.setOnReviewDeleted(id -> {
           list.removeIf(r -> r.getId() == id);
           detailController.updateStats(list);
        });
    }
}
