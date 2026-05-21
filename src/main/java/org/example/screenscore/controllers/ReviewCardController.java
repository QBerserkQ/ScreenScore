package org.example.screenscore.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

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

    private Consumer<Integer> onDelete;
    private Consumer<ReviewClass> onReviewUpdated;

    public void setOnDelete(Consumer<Integer> callback) {
        this.onDelete = callback;
    }
    public void setOnReviewUpdated(Consumer<ReviewClass> callback) {
        this.onReviewUpdated = callback;
    }
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
            style = "-fx-background-color: #00839e;";
        else
            style = "-fx-background-color: #e95ea6;";

        vbox.setStyle(style);
    }

    private void setUrlLabel(){
        String localPath = review.getImageUrl();

        if(localPath == null || localPath.isEmpty()){
            urlLabel.setImage(null);
            return;
        }

        File file = new File(localPath);
        if(!file.exists()){
            urlLabel.setImage(null);
            return;
        }

        Image image = new Image(file.toURI().toString());
        urlLabel.setImage(image);
    }

    public void onDeleteButtonClicked() {
        onDelete.accept(review.getId());
    }

    public void onUpdateButtonClicked(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views/review_creator.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Update Review");
            stage.setScene(new Scene(root));

            ReviewCreatorController controller = loader.getController();
            controller.setEditMode(review);
            controller.setOnReviewUpdated(review -> {
                if(onReviewUpdated != null){
                    onReviewUpdated.accept(review);
                }
                stage.close();
            });

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onReviewCardClicked() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views" +
                    "/review_card_full_template.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Full information");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            FullReviewCardController controller = loader.getController();
            controller.addInfo(review.getTitle(), review.getImageUrl(),
                    review.getRating(), review.getDescription());

            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
