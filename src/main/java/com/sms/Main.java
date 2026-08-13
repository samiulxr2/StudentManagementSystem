package com.sms;

import com.sms.config.AppConfig;
import com.sms.utils.JsonFileInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        JsonFileInitializer.initialize();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/Login.fxml")
        );

        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(
                getClass().getResource("/css/app.css").toExternalForm()
        );

        stage.setTitle(AppConfig.APP_NAME);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}