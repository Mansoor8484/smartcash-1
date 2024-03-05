package org.example.sclogin1;

//package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import java.util.Optional;


public class buttonsOverview extends Application {

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
        //overAccount.setFont(Font.font("Cambria", FontWeight.BOLD, FontPosture.REGULAR, 14));
        overAccount.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        overAccount.setTextFill(Paint.valueOf("#3b3b3b"));
        overAccount.setBackground(new Background(new BackgroundFill(Color.web("#DFF0D8"), CornerRadii.EMPTY, Insets.EMPTY)));

        Button transactions = new Button("Transactions");
        transactions.setOnAction(e -> showTransactionsPage(primaryStage));
        transactions.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        transactions.setTextFill(Paint.valueOf("#3b3b3b"));
        transactions.setBackground(new Background(new BackgroundFill(Color.web("#DFF0D8"), CornerRadii.EMPTY, Insets.EMPTY)));

        Button budgeting = new Button("Budgeting");
        budgeting.setOnAction(e -> showBudgetingPage(primaryStage));
        budgeting.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        budgeting.setTextFill(Paint.valueOf("#3b3b3b"));
        budgeting.setBackground(new Background(new BackgroundFill(Color.web("#DFF0D8"), CornerRadii.EMPTY, Insets.EMPTY)));

        Button security = new Button("Security");
        security.setOnAction(e -> showSecurityPage(primaryStage));
        security.setStyle("-fx-font-size: 14; -fx-font-weight: bold");
        security.setTextFill(Paint.valueOf("#3b3b3b"));
        security.setBackground(new Background(new BackgroundFill(Color.web("#DFF0D8"), CornerRadii.EMPTY, Insets.EMPTY)));

        MenuButton notificationMenuButton = new MenuButton("Settings");
        notificationMenuButton.setTooltip(new Tooltip("Notification Settings"));
        MenuItem changeNotifications = new MenuItem("Change Notification Settings");
        changeNotifications.setOnAction(event -> {

                    // create a dialog to let the user choose between SMS and email notifications
                    Dialog notificationChoice = new Dialog<>();
                    notificationChoice.setTitle("Notification Settings");
                    notificationChoice.setHeaderText("How do you want to receive notifications?");

                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
                    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    notificationChoice.getDialogPane().getButtonTypes().addAll(cancelButton, okButton);

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.add(new Label("Preferred Notification Method:"), 0, 0);

                    ChoiceBox notificationMethod = new ChoiceBox<>();
                    notificationMethod.getItems().addAll("SMS", "Email");
                    grid.add(notificationMethod, 1, 0);

                    notificationChoice.getDialogPane().setContent(grid);
                    notificationChoice.getDialogPane().getButtonTypes().remove(ButtonType.OK);


            Optional result = notificationChoice.showAndWait();
            if (result.isPresent() && result.get() != null) {

                // user selected notification method, now show confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Notification Change");
                alert.setHeaderText("Are you sure you want to change your notification settings?");
                alert.setContentText("You will not be able to revert this action.");

                Optional confirmationResult = alert.showAndWait();
                if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                    // user clicked OK, so proceed with changing notification settings based on their selection in the previous dialog
                    Object selectedMethod = notificationMethod.getValue();
                    if (selectedMethod == null) {
                        // user didn't make selection, so do nothing or show an error message as appropriate for your application
                    } else {
                        // implement  logic  for changing email or SMS notifications based on user selection
                    }
                } else {
                    // user clicked Cancel or closed dialog box, so do nothing or show an error message as appropriate for your application
                }
            }
        });

        MenuItem recentActivity = new MenuItem("Recent Account Activity");
        MenuItem logout = new MenuItem("Sign Out");
        logout.setOnAction(e -> showLoginScreen(primaryStage));

        notificationMenuButton.getItems().addAll(changeNotifications, recentActivity, logout);


        // welcome message
        //Label welcomeMessage = new Label("Welcome to SmartCash, USER_NAME");


        // Logout button
        //Button logoutButton = new Button("Logout");
        //logoutButton.setOnAction(e -> showLoginScreen(primaryStage));



        // Layout
        VBox rightLayout = new VBox(10);
        rightLayout.getChildren().add(notificationMenuButton);
        for(Node node : rightLayout.getChildren()) {
            VBox.setMargin(node, new Insets(5, 10, 5, 10));
        }

        //VBox root = new VBox(10);
        //root.setPadding(new Insets(10));

        HBox buttonBox = new HBox(10);
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(overAccount, transactions, budgeting, security);
        for (Node node : buttonBox.getChildren()) {
            HBox.setMargin(node, new Insets(5, 50, 5, 50));
        }
        buttonBox.setPrefSize(150, 50);
        //rightLayout.getChildren().addAll(buttonBox);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(buttonBox);
        borderPane.setRight(rightLayout);
        borderPane.setBackground(new Background(new BackgroundFill(Color.web("3b3b3b"), CornerRadii.EMPTY, Insets.EMPTY)));


        // Scene
        Scene accountMainScene = new Scene(borderPane, 1650, 1080);
        primaryStage.setScene(accountMainScene);

        // Show the stage
        primaryStage.show();


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static void showLoginScreen(Stage primaryStage) {
        // Create a new instance of the HelloApplication class (your login screen)
        Login loginScreen = new Login();
        loginScreen.start(primaryStage);
    }

    private static void showTransactionsPage(Stage primaryStage) {

        transactionsPage TransactionsPage = new transactionsPage();
        TransactionsPage.showTransactionsPage(primaryStage);
    }

    private static void showSecurityPage(Stage primaryStage) {
        securityPage SecurityPage = new securityPage();
        SecurityPage.showSecurityPage(primaryStage);

    }


    private static void showBudgetingPage(Stage primaryStage) {
        // Create an instance of the budgetingPage class
        budgetingPage BudgetingPage = new budgetingPage();

        // Call the method to show the contents of the budgeting page
        BudgetingPage.showBudgetingPage(primaryStage);
    }



}


