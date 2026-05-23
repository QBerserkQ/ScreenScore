package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.services.ReviewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public TextField searchField;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    private ReviewService reviewService;

    private List<ReviewClass> listReviews = new ArrayList();

    @FXML
    public void initialize() {
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
           applyFilters(newValue);
        });
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setListReviews(List<ReviewClass> listReviews) {
        clearReviewCard();
        this.listReviews = listReviews;
        addAllReviews();
    }

    public void addNewReview(ReviewClass review) {
        listReviews.add(review);
        renderCard(review);
    }

    private void addAllReviews(){
        for (ReviewClass r : listReviews){
            renderCard(r);
        }
    }

    private void renderCard(ReviewClass review) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views/review_card_template.fxml"));
            Parent card = loader.load();

            ReviewCardController cardController = loader.getController();
            cardController.setData(review);

            cardController.setOnDelete(id -> {
                reviewService.deleteReview(id);
                flowPane.getChildren().remove(card);
                listReviews.remove(review);
            });

            cardController.setOnReviewUpdated(rw -> {
                reviewService.updateReview(rw);
                cardController.setData(rw);
            });

            flowPane.getChildren().add(card);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clearReviewCard() {
        flowPane.getChildren().clear();
    }

    private void applyFilters(String newValue) {
        clearReviewCard();

        String filter = newValue.trim().toLowerCase();

        for (ReviewClass review : listReviews) {
            if(review.getTitle().toLowerCase().contains(filter)) {
                renderCard(review);
            }
        }
    }
}
