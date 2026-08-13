package com.sms.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    public static void switchScene(Stage stage, String fxml, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    SceneManager.class.getResource("/css/app.css").toExternalForm()
            );

            stage.setTitle(title);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}