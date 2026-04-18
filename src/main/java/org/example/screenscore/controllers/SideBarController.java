package org.example.screenscore.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SideBarController {

    public void onNewReviewClicked(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/screenscore/views/review_creator.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("New Review");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
