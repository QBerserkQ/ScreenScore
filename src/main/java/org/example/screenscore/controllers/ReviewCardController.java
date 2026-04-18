package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.screenscore.models.ReviewClass;

public class ReviewCardController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView urlLabel;

    public void setData(ReviewClass review) {
        titleLabel.setText(review.getTitle());
        ratingLabel.setText(String.valueOf(review.getRating()));
        descriptionLabel.setText(review.getDescription());

        String url = review.getImageUrl();

        if (url != null && !url.isBlank()) {
            try {
                Image img = new Image(url, true);
                urlLabel.setImage(img);
            } catch (Exception e) {
                System.out.println("Bad image URL: " + url);
                e.printStackTrace();
            }
        } else {
            urlLabel.setImage(null);
        }
    }
}
