package com.sms.controller;

import com.sms.session.Session;
import com.sms.utils.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SettingsController {

    // =========================================================
    // SETTINGS UI
    // =========================================================

    @FXML
    private Label usernameLabel;

    @FXML
    private Label roleLabel;


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    public void initialize() {

        // Show your name
        usernameLabel.setText("Md. Samiul Islam");

        // Keep role as ADMIN
        roleLabel.setText("ADMIN");
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    @FXML
    private void logout() {

        // Clear current session
        Session.logout();

        // Get current application window
        Stage stage =
                (Stage) usernameLabel
                        .getScene()
                        .getWindow();

        // Go back to Login page
        SceneManager.switchScene(
                stage,
                "Login.fxml",
                "Student Management System"
        );
    }
}