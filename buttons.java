package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class buttons extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        createBankGUI(primaryStage);
    }

    public static void createBankGUI(Stage primaryStage) {
        primaryStage.setTitle("SmartCash");

        // Buttons
        Button overAccount = new Button("Account Overview");
        Button transaction = new Button("Transactions");
        Button budgeting = new Button("Budgeting");
        Button security = new Button("Security");

        // Welcome message
        Label welcomeMessage = new Label("Welcome to SmartCash, USER_NAME");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> showLoginScreen(primaryStage));

        // Set button actions
        budgeting.setOnAction(e -> showBudgetingPage(primaryStage));

        // Layout
        VBox root = new VBox(10);
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(overAccount, transaction, budgeting, security, logoutButton);
        root.getChildren().addAll(buttonBox, welcomeMessage);
        root.setPadding(new Insets(10));

        // Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private static void showLoginScreen(Stage primaryStage) {
        // Create a new instance of the HelloApplication class (your login screen)
        HelloApplication loginScreen = new HelloApplication();
        loginScreen.start(primaryStage);
    }

    private static void showBudgetingPage(Stage primaryStage) {
        // Create an instance of the budgetingPage class
        budgetingPage BudgetingPage = new budgetingPage();

        // Call the method to show the contents of the budgeting page
        BudgetingPage.showBudgetingPage(primaryStage);
    }
}







