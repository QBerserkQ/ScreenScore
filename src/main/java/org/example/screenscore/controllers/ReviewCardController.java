package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReviewCardController {
    @FXML
    private VBox vbox;
    @FXML
    private Label titleLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView urlLabel;

    private ReviewClass review;

    public void setData(ReviewClass review) {
        this.review = review;
        render();
    }

    private void render() {
        setTitleLabel();
        setRatingLabel();
        setDescriptionLabel();

        setStyleType();

        setUrlLabel();
    }

    private void setTitleLabel(){
        titleLabel.setText(review.getTitle());
    }

    private void setRatingLabel(){
        ratingLabel.setText(String.valueOf(review.getRating()));
    }

    private void setDescriptionLabel(){
        descriptionLabel.setText(review.getDescription());
    }

    private void setStyleType(){
        String style;
        Type t = review.getType();

        if(t == Type.Movie)
            style = "-fx-background-color: #00739e;";
        else if(t == Type.Series)
            style = "-fx-background-color: #003049;";
        else
            style = "-fx-background-color: #e95ea6;";

        vbox.setStyle(style);
    }

    private void setUrlLabel(){
        String url = review.getImageUrl();

        if (url != null && !url.isBlank()) {
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();

                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                InputStream inputStream = connection.getInputStream();
                Image image = new Image(inputStream);

                urlLabel.setImage(image);
            } catch (Exception e) {
                System.out.println("Bad image URL: " + url);
                e.printStackTrace();
            }
        } else {
            urlLabel.setImage(null);
        }
    }
}
