package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.prefs.Preferences;

public class HelloApplication extends Application {
    private static final String PIN_KEY = "user_pin";


    private String registeredPIN;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        registeredPIN = loadPIN();
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

        // Add components to the grid
        Label pinLabel = new Label("Enter PIN:");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter your PIN");

        Label reEnterPinLabel = new Label("Re-enter PIN:");
        PasswordField reEnterPinField = new PasswordField();
        reEnterPinField.setPromptText("Re-enter your PIN");

        Button loginButton = new Button("Login");

        // Create a hyperlink for registration
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register Here");

        // Set the action for the login button
        loginButton.setOnAction(e -> {
            String enteredPIN = pinField.getText();
            if (isRegistrationComplete() && isValidPIN(enteredPIN) && enteredPIN.equals(registeredPIN)) {
                showInfoAlert("Sign In Successful", "Welcome back!");
                savePIN(enteredPIN);
                showSuccessPage(primaryStage);
                buttons.createBankGUI(new Stage());




                // Add logic to open the main banking application window
            } else {
                showAlert("Sign In Failed", "Invalid PIN. Please try again.");
                clearFields(pinField, reEnterPinField);
            }
        });



        // Set the action for the register link
        registerLink.setOnAction(e -> showRegistrationWindow());

        // Add components to the grid
        grid.add(pinLabel, 0, 0);
        grid.add(pinField, 1, 0);
        grid.add(reEnterPinLabel, 0, 1);
        grid.add(reEnterPinField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Apply styling to the grid
        grid.setStyle("-fx-background-color: #DFF0D8; -fx-border-color: #4CAF50; -fx-border-width: 2px; -fx-border-radius: 5px;");

        // Add components to the root VBox
        root.getChildren().addAll(titleLabel, grid, registerLink);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void savePIN(String pin) {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        prefs.put(PIN_KEY, pin);
    }

    private String loadPIN() {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        return prefs.get(PIN_KEY, null);
    }

    private void showSuccessPage(Stage primaryStage) {
        // Create a VBox layout for the success page
        VBox successRoot = new VBox(20);
        successRoot.setAlignment(Pos.CENTER);

        // Create a label for success message
        Label successLabel = new Label("Login Successful! Redirecting to Home Page...");

        // Add components to the success page
        successRoot.getChildren().addAll(successLabel);

        // Create a scene for the success page
        Scene successScene = new Scene(successRoot, 400, 200);

        // Set the success scene on the stage
        primaryStage.setScene(successScene);

        // Close the login stage
        primaryStage.close();




    }

    private void showRegistrationWindow() {
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Register");

        VBox registrationRoot = new VBox(20);
        registrationRoot.setAlignment(Pos.CENTER);
        registrationRoot.setPadding(new Insets(20));

        Label enterPinLabel = new Label("Enter PIN:");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter your PIN");

        Label reEnterPinLabel = new Label("Re-enter PIN:");
        PasswordField reEnterPinField = new PasswordField();
        reEnterPinField.setPromptText("Re-enter your PIN");

        // Requirements as bullet points
        Label requirementsLabel = new Label("PIN Requirements:");
        requirementsLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        Label requirement1 = new Label("PIN must not contain 3 sequential numbers.");
        Label requirement2 = new Label("PIN must have a length between 10 and 12 digits.");

        // Add components to the registration VBox
        registrationRoot.getChildren().addAll(enterPinLabel, pinField, reEnterPinLabel, reEnterPinField, requirementsLabel, requirement1, requirement2);

        // Create a button to complete registration
        Button registerButton = new Button("Register");

        // Set the action for the register button
        registerButton.setOnAction(e -> {
            String enteredPIN = pinField.getText();
            String reEnteredPIN = reEnterPinField.getText();

            boolean hasSequentialNumbers = containsSequentialNumbers(enteredPIN);
            boolean isValidLength = isValidLength(enteredPIN);

            if (hasSequentialNumbers && isValidLength) {
                showInfoAlert("Registration Successful", "Please login with your new PIN.");
                registeredPIN = enteredPIN;
                clearFields(pinField, reEnterPinField);
                registrationStage.close();
            } else if (hasSequentialNumbers) {
                showAlertI("Registration Failed", "Invalid PIN. Please choose a PIN without 3 sequential numbers.");
            } else if (!isValidLength) {
                showAlertI("Registration Failed", "Invalid PIN length. Please choose a PIN with a length between 10 and 12 digits.");
            } else if (enteredPIN.equals(reEnteredPIN)) {
                showAlert("Registration Succesfull", "Please login with your new PIN");
                registeredPIN = enteredPIN;
                clearFields(pinField, reEnterPinField);
                registrationStage.close();
            }
        });

        // Add components to the registration VBox
        registrationRoot.getChildren().addAll(registerButton);

        // Create a scene and set it on the registration stage
        Scene registrationScene = new Scene(registrationRoot, 400, 400);
        registrationStage.setScene(registrationScene);

        // Show the registration stage
        registrationStage.show();
    }

    private boolean isValidLength(String pin) {
        return pin.length() >= 10 && pin.length() <= 12;
    }

    // Example PIN validation logic (replace with your own)
    private boolean isValidPIN(String pin) {
        return pin.matches("\\d{10,12}") && !containsSequentialNumbers(pin);
    }

    private boolean isRegistrationComplete() {
        return registeredPIN != null;
    }

    private boolean containsSequentialNumbers(String pin) {
        return pin.matches(".*(012|123|234|345|456|567|678|789|890).*");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoAlertI(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to show alerts
    private void showAlertI(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to clear text fields
    private void clearFields(PasswordField... fields) {
        for (PasswordField field : fields) {
            field.clear();
        }
    }
}
