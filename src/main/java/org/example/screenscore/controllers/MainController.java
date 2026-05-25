package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import org.example.screenscore.models.Filters;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.services.ReviewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainController {
    @FXML
    private TextField searchField;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public ChoiceBox<String> FilterChoiceBox;

    private ReviewService reviewService;

    private List<ReviewClass> listReviews = new ArrayList();

    private Filters filter = Filters.Rating_Desc;

    @FXML
    public void initialize() {
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());

        FilterChoiceBox.getItems().addAll(Filters.getFilters());
        FilterChoiceBox.setOnAction(e -> {
            this.filter = Filters.getFilter(FilterChoiceBox.getValue());
            applyFilters(searchField.getText());
        });

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
        applyFilters(searchField.getText());
    }

    public void addNewReview(ReviewClass review) {
        listReviews.add(review);
        renderCard(review);
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

        List<ReviewClass> tmp = new ArrayList<>(listReviews);
        tmp.removeIf(r -> !(r.getTitle().toLowerCase().contains(filter)));
        sortReviews(tmp);

        for (ReviewClass review : tmp) {
            renderCard(review);
        }
    }

    private void sortReviews(List<ReviewClass> r) {
        switch (filter) {
            case Alpha_Asc -> r.sort(alphaAsc);
            case Alpha_Desc -> r.sort(alphaAsc.reversed());
            case Rating_Asc -> r.sort(ratingAsc);
            case Rating_Desc -> r.sort(ratingAsc.reversed());
        }
    }

    private final Comparator<ReviewClass> alphaAsc =
            Comparator.comparing(t -> t.getTitle().toLowerCase());

    private final Comparator<ReviewClass> ratingAsc =
            Comparator.comparing(ReviewClass::getRating);
}
