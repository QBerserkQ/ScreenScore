package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class FullReviewCardController {

    @FXML
    private Label titleFull;

    @FXML
    private ImageView imageFull;

    @FXML
    private Label ratingFull;

    @FXML
    private Label descriptionFull;

    public void addInfo(String title, String imageUrl, int rating, String description) {
        titleFull.setText(title);
        ratingFull.setText(String.valueOf(rating));
        descriptionFull.setText(description);

        if(imageUrl == null || imageUrl.isEmpty()){
            imageFull.setImage(null);
            return;
        }

        File file = new File(imageUrl);
        if(!file.exists()){
            imageFull.setImage(null);
            return;
        }

        Image image = new Image(file.toURI().toString());
        imageFull.setImage(image);
    }
}
