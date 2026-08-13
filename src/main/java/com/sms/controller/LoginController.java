package com.sms.controller;

import com.sms.service.LoginService;
import com.sms.utils.AlertUtil;
import com.sms.utils.SceneManager;
import com.sms.utils.ValidationUtil;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final LoginService loginService =
            new LoginService();

    // Prevent multiple login clicks
    private boolean loginInProgress = false;

    // Loading overlay
    private StackPane loadingOverlay;


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    private void initialize() {

        // Clear previous message
        if (messageLabel != null) {
            messageLabel.setText("");
        }

        // ENTER in username -> password
        usernameField.setOnAction(event ->
                passwordField.requestFocus()
        );

        // ENTER in password -> login
        passwordField.setOnAction(event ->
                login()
        );
    }


    // =========================================================
    // LOGIN
    // =========================================================

    @FXML
    private void login() {

        // Prevent double click / repeated ENTER
        if (loginInProgress) {
            return;
        }

        String username =
                usernameField.getText().trim();

        String password =
                passwordField.getText().trim();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (ValidationUtil.isEmpty(username)) {

            messageLabel.setText(
                    "Please enter your username."
            );

            usernameField.requestFocus();

            return;
        }


        if (ValidationUtil.isEmpty(password)) {

            messageLabel.setText(
                    "Please enter your password."
            );

            passwordField.requestFocus();

            return;
        }


        // Clear old message
        messageLabel.setText("");

        // Start loading
        startLoading();


        // =====================================================
        // BACKGROUND AUTHENTICATION
        // =====================================================

        Task<Boolean> loginTask =
                new Task<>() {

                    @Override
                    protected Boolean call() {

                        return loginService.authenticate(
                                username,
                                password
                        );
                    }
                };


        // =====================================================
        // LOGIN SUCCESS / FAILURE
        // =====================================================

        loginTask.setOnSucceeded(event -> {

            boolean authenticated =
                    loginTask.getValue();


            // =================================================
            // SUCCESS
            // =================================================

            if (authenticated) {

                /*
                 * Keep loading visible for a short moment
                 * so the user can actually see the animation.
                 */
               PauseTransition delay =
        new PauseTransition(
                Duration.seconds(3)
        );

                delay.setOnFinished(e -> {

                    try {

                        Stage stage =
                                (Stage) usernameField
                                        .getScene()
                                        .getWindow();


                        SceneManager.switchScene(
                                stage,
                                "Dashboard.fxml",
                                "Student Management System"
                        );

                    } catch (Exception ex) {

                        ex.printStackTrace();

                        stopLoading();

                        messageLabel.setText(
                                "Dashboard could not be opened."
                        );

                        AlertUtil.error(
                                "Navigation Error",
                                "Login successful, but Dashboard.fxml could not be opened.\n\n"
                                        + ex.getMessage()
                        );

                        loginInProgress = false;
                    }
                });

                delay.play();

                return;
            }


            // =================================================
            // LOGIN FAILED
            // =================================================

            stopLoading();

            loginInProgress = false;

            messageLabel.setText(
                    "Invalid username or password."
            );

            AlertUtil.error(
                    "Login Failed",
                    "Invalid Username or Password."
            );

            passwordField.clear();

            passwordField.requestFocus();
        });


        // =====================================================
        // BACKGROUND ERROR
        // =====================================================

        loginTask.setOnFailed(event -> {

            stopLoading();

            loginInProgress = false;

            Throwable error =
                    loginTask.getException();

            if (error != null) {
                error.printStackTrace();
            }

            messageLabel.setText(
                    "Unable to complete login."
            );

            AlertUtil.error(
                    "Login Error",
                    "An error occurred while trying to login."
            );
        });


        // =====================================================
        // START TASK
        // =====================================================

        Thread loginThread =
                new Thread(loginTask);

        loginThread.setDaemon(true);

        loginThread.start();
    }


    // =========================================================
    // START LOADING ANIMATION
    // =========================================================

    private void startLoading() {

        loginInProgress = true;


        // Find the login button
        Node loginButton =
                usernameField
                        .getScene()
                        .lookup(".login-button");


        if (loginButton instanceof Button button) {

            button.setDisable(true);

            button.setText(
                    "Signing in..."
            );
        }


        // Disable input fields
        usernameField.setDisable(true);
        passwordField.setDisable(true);


        // Prevent duplicate overlay
        if (loadingOverlay != null) {
            return;
        }


        // =====================================================
        // GET LOGIN ROOT
        // =====================================================

        if (!(usernameField.getScene().getRoot()
                instanceof BorderPane root)) {

            return;
        }


        if (!(root.getCenter()
                instanceof StackPane loginBackground)) {

            return;
        }


        // =====================================================
        // CREATE OVERLAY
        // =====================================================

        loadingOverlay =
                new StackPane();


        loadingOverlay.setMaxSize(
                Double.MAX_VALUE,
                Double.MAX_VALUE
        );


        loadingOverlay.setStyle(
                "-fx-background-color: rgba(4, 15, 30, 0.82);"
        );


        // =====================================================
        // LOADING CONTENT
        // =====================================================

        VBox loadingBox =
                new VBox(16);

        loadingBox.setAlignment(
                javafx.geometry.Pos.CENTER
        );


        // =====================================================
        // SPINNER
        // =====================================================

        ProgressIndicator progressIndicator =
                new ProgressIndicator();

        progressIndicator.setPrefSize(
                65,
                65
        );


        progressIndicator.setStyle(
                "-fx-progress-color: #19C4D0;"
        );


        // =====================================================
        // LOADING TEXT
        // =====================================================

        Label loadingLabel =
                new Label(
                        "Signing in..."
                );

        loadingLabel.setStyle(
                "-fx-text-fill: white;"
                        + "-fx-font-size: 18px;"
                        + "-fx-font-weight: bold;"
        );


        Label pleaseWaitLabel =
                new Label(
                        "Please wait"
                );

        pleaseWaitLabel.setStyle(
                "-fx-text-fill: #8FAFC2;"
                        + "-fx-font-size: 12px;"
        );


        loadingBox.getChildren().addAll(
                progressIndicator,
                loadingLabel,
                pleaseWaitLabel
        );


        loadingOverlay
                .getChildren()
                .add(loadingBox);


        // =====================================================
        // ADD OVERLAY
        // =====================================================

        loginBackground
                .getChildren()
                .add(loadingOverlay);


        // =====================================================
        // FADE-IN ANIMATION
        // =====================================================

        loadingOverlay.setOpacity(0);


        FadeTransition fadeIn =
                new FadeTransition(
                        Duration.millis(250),
                        loadingOverlay
                );

        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeIn.play();
    }


    // =========================================================
    // STOP LOADING ANIMATION
    // =========================================================

    private void stopLoading() {

        Platform.runLater(() -> {

            // Re-enable fields
            usernameField.setDisable(false);
            passwordField.setDisable(false);


            // Restore login button
            Node loginButton =
                    usernameField
                            .getScene()
                            .lookup(".login-button");


            if (loginButton instanceof Button button) {

                button.setDisable(false);

                button.setText(
                        "Sign In"
                );
            }


            // Remove overlay
            if (loadingOverlay != null) {

                if (loadingOverlay.getParent()
                        instanceof StackPane parent) {

                    parent.getChildren()
                            .remove(loadingOverlay);
                }

                loadingOverlay = null;
            }
        });
    }
}