package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.services.ReviewService;

import java.io.IOException;

public class MainController {
    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    private ReviewService reviewService;

    @FXML
    public void initialize() {
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void addReviewCard(ReviewClass review) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views/review_card_template.fxml"));
            Parent card = loader.load();

            ReviewCardController cardController = loader.getController();
            cardController.setData(review);

            cardController.setOnDelete(id -> {
                reviewService.deleteReview(id);
                flowPane.getChildren().remove(card);
            });

            flowPane.getChildren().add(card);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
