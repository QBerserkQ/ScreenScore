package org.example.screenscore.controllers;

import java.util.function.Consumer;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;
import org.example.screenscore.services.ImageService;

public class ReviewCreatorController {
    @FXML
    private TextField titleField;
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
    @FXML
    private Button createReviewButton;

    private boolean isEditMode = false;
    private ReviewClass reviewToEdit;

    private Consumer<ReviewClass> onReviewCreated;
    private Consumer<ReviewClass> onReviewUpdated;
    private final ImageService imageService = new ImageService();

    public void onCreateReviewButtonClicked() {
        if (isEditMode) {
            String oldTitle = reviewToEdit.getTitle();
            readData(reviewToEdit);

            if(!oldTitle.equals(reviewToEdit.getTitle())) {
                Task<String> task = new Task<>(){
                    protected String call() throws Exception {
                        return imageService.download(reviewToEdit.getTitle(), reviewToEdit.getType());
                    }
                };

                task.setOnSucceeded(event -> {
                    reviewToEdit.setImageUrl(task.getValue());
                    onReviewUpdated.accept(reviewToEdit);
                });

                task.setOnFailed(event -> onReviewUpdated.accept(reviewToEdit));
                Thread thread = new Thread(task);
                thread.start();
            }else{
                onReviewUpdated.accept(reviewToEdit);
            }

        }else {
            ReviewClass reviewClass = new ReviewClass();
            readData(reviewClass);

            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return imageService.download(reviewClass.getTitle(), reviewClass.getType());
                }
            };

            task.setOnSucceeded(event -> {
                reviewClass.setImageUrl(task.getValue());
                onReviewCreated.accept(reviewClass);
            });

            task.setOnFailed(event -> {
                task.getException().printStackTrace();
                onReviewCreated.accept(reviewClass);
            });

            new Thread(task).start();
        }
    }

    public void setOnReviewCreated(Consumer<ReviewClass> callback) {
        this.onReviewCreated = callback;
    }

    public void setOnReviewUpdated(Consumer<ReviewClass> callback) {
        this.onReviewUpdated = callback;
    }

    public void setEditMode(ReviewClass review) {
        createReviewButton.setText("Save");
        this.isEditMode = true;
        this.reviewToEdit = review;
        titleField.setText(reviewToEdit.getTitle());
        ratingField.setText(reviewToEdit.getRating() + "");
        descriptionField.setText(reviewToEdit.getDescription());

        if(reviewToEdit.getType() == Type.Movie) {
            movieRbutton.setSelected(true);
        }else if(reviewToEdit.getType() == Type.Series) {
            seriesRbutton.setSelected(true);
        }else if(reviewToEdit.getType() == Type.Anime) {
            animeRbutton.setSelected(true);
        }
    }

    private void readData(ReviewClass rw){
        String title = titleField.getText();

        int rating;
        try{
            rating = Integer.parseInt(ratingField.getText());
        }catch(NumberFormatException e){
            rating = 0;
            e.printStackTrace();
        }


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

        rw.setTitle(title);
        rw.setRating(rating);
        rw.setDescription(description);
        rw.setType(type1);
    }
}
