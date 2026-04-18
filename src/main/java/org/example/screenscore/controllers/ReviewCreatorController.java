package org.example.screenscore.controllers;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

public class ReviewCreatorController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField descriptionField;

    @FXML
    private RadioButton movieRbutton;
    @FXML
    private RadioButton seriesRbutton;
    @FXML
    private RadioButton animeRbutton;
    @FXML
    private ToggleGroup type;



    private Consumer<ReviewClass> onReviewCreated;

    public void onCreateReviewButtonClicked() {
        String title = titleField.getText();
        String url = urlField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        if(rating < 0) rating = 0;
        if(rating > 10) rating = 10;
        String description = descriptionField.getText();
        Type type1;

        Toggle t = type.getSelectedToggle();
        if(t == movieRbutton) {
            type1 = Type.Movie;
        }else if(t == seriesRbutton) {
            type1 = Type.Series;
        }else if(t == animeRbutton) {
            type1 = Type.Anime;
        }else  {
            type1 = Type.Movie;
        }

        ReviewClass reviewClass = new ReviewClass(rating, title, description, type1, url);

        onReviewCreated.accept(reviewClass);
    }

    public void setOnReviewCreated(Consumer<ReviewClass> callback) {
        this.onReviewCreated = callback;
    }
}
