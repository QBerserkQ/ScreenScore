package org.example.screenscore.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.screenscore.models.ReviewClass;
import org.example.screenscore.models.Type;

import java.io.IOException;
import java.util.function.Consumer;

public class SideBarController {
    private Consumer<ReviewClass> onReviewCreated;
    private Consumer<Type> onReviewType;

    public void setOnReviewCreated(Consumer<ReviewClass> callback) {
        this.onReviewCreated = callback;
    }
    public void setOnReviewType(Consumer<Type> callback) {
        this.onReviewType = callback;
    }

    public void onNewReviewClicked(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views/review_creator.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("New Review");
            stage.setScene(new Scene(root));
            stage.show();

            ReviewCreatorController controller = loader.getController();
            controller.setOnReviewCreated(review -> {
                if(onReviewCreated != null){
                    onReviewCreated.accept(review);
                }
                stage.close();
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onMovieReviewsClicked() {
        onReviewType.accept(Type.Movie);
    }

    public void onSeriesReviewsClicked() {
        onReviewType.accept(Type.Series);
    }

    public void onAnimeReviewsClicked() {
        onReviewType.accept(Type.Anime);
    }

    public void onAllReviewsClicked() {
        onReviewType.accept(null);
    }
}
