package org.example.sclogin1;

//package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class Login extends Application {
    private Map<String, String> registeredUsers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        registeredUsers = new HashMap<>();
        primaryStage.setTitle("Welcome to SmartCash");

        // Create a VBox layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Create a GridPane for the login/register form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setPadding(new Insets(20));

        // Create title
        Label titleLabel = new Label("Sign In to Your Account");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        titleLabel.setTextFill(Paint.valueOf("#DFF0D8"));


        // Add components to the grid
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        loginButton.setTextFill(Paint.valueOf("#3b3b3b"));
        loginButton.setBackground(new Background(new BackgroundFill(Color.web("#4CAF50"), CornerRadii.EMPTY, Insets.EMPTY)));


        // Create a hyperlink for registration
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register Here");
        registerLink.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        registerLink.setTextFill(Paint.valueOf("#DFF0D8"));


        // Set the action for the login button
        loginButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();
            if (isValidCredentials(enteredUsername, enteredPassword)) {
                showInfoAlert("Sign In Successful", "Welcome back, " + enteredUsername + "!");
                clearFields(usernameField, passwordField);
                openButtonsPage(primaryStage);
            } else {
                showAlert("Sign In Failed", "Invalid username or password. Please try again.");
                clearFields(passwordField);
            }
        });

        // Set the action for the register link
        registerLink.setOnAction(e -> showRegistrationWindow());

        // Add components to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Apply styling to the grid
        grid.setStyle("-fx-background-color: #DFF0D8; -fx-border-color: #4CAF50; -fx-border-width: 2px; -fx-border-radius: 5px;");

        // Add components to the root VBox
        root.getChildren().addAll(titleLabel, grid, registerLink);

        // Create a scene and set it on the stage
        Scene loginScene = new Scene(root, 1650, 1080);
        root.setStyle("-fx-background-color: #3b3b3b;");
        primaryStage.setScene(loginScene);

        // Show the stage
        primaryStage.show();
    }

    private void openButtonsPage(Stage primaryStage) {
        Stage buttonsStage = new Stage();
        buttonsStage.setTitle("Buttons Page");

        // Call createBankGUI() method from the Buttons class to create the UI
        buttonsOverview.createBankGUI(new Stage());

        // Close the login window
        primaryStage.close();

        // Show the buttons page window

    }

    private void registerUser(String username, String password) {
        registeredUsers.put(username, password);
    }

    private boolean isValidCredentials(String username, String password) {
        String storedPassword = registeredUsers.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private void showRegistrationWindow() {
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Register");

        VBox registrationRoot = new VBox(20);
        registrationRoot.setAlignment(Pos.CENTER);
        registrationRoot.setPadding(new Insets(20));

        Label usernameLabel = new Label("Username:");
        //usernameLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Add components to the registration VBox
        registrationRoot.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);

        // Create a button to complete registration
        Button registerButton = new Button("Register");

        // Set the action for the register button
        registerButton.setOnAction(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            if (!registeredUsers.containsKey(enteredUsername)) {
                registerUser(enteredUsername, enteredPassword);
                showInfoAlert("Registration Successful", "You have successfully registered. Please login.");
                clearFields(usernameField, passwordField);
                registrationStage.close();
            } else {
                showAlert("Registration Failed", "Username already exists. Please choose a different username.");
            }
        });

        // Add components to the registration VBox
        registrationRoot.getChildren().addAll(registerButton);

        // Create a scene and set it on the registration stage
        Scene registrationScene = new Scene(registrationRoot, 400, 200);
        registrationStage.setScene(registrationScene);

        // Show the registration stage
        registrationStage.show();
    }


    // Helper method to show information alerts
    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to show alerts
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear text fields
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}