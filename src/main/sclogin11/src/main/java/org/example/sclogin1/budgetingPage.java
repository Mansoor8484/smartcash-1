package org.example.sclogin1;

//package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class budgetingPage extends Application {

    private double balance;
    private double income;
    private double expenses;

    private Label balanceLabel;
    private TextField incomeTextField;
    private TextField expenseTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showBudgetingPage(primaryStage);
    }
    public void showBudgetingPage(Stage primaryStage) {
        primaryStage.setTitle("Budgeting");

        StackPane budgetingRoot = new StackPane();
        budgetingRoot.setBackground(new Background(new BackgroundFill(Color.web("3b3b3b"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label budgetingLabel = new Label("Budgeting");
        budgetingLabel.setUnderline(true);
        budgetingLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 28));
        budgetingLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        budgetingRoot.getChildren().add(budgetingLabel);
        StackPane.setAlignment(budgetingLabel, Pos.TOP_LEFT);
        budgetingLabel.setPadding(new Insets(30,0,0,40));

        GridPane budgetingGrid = createBudgetingGrid();

        // create a StackPane to hold both budgetingRoot and budgetingGrid
        StackPane budgetStackPane = new StackPane();
        budgetStackPane.getChildren().addAll(budgetingRoot, budgetingGrid);

        Scene combinedScene = new Scene(budgetStackPane, 1650, 1080);

        primaryStage.setScene(combinedScene);
        primaryStage.show();


    }

    private GridPane createBudgetingGrid() {
        GridPane grid = new GridPane(800, 300);
        grid.setPadding(new Insets(80, 10, 10, 800));
        grid.setVgap(5);
        grid.setHgap(5);

        balanceLabel = new Label("Current Balance: $");
        balanceLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 14));
        balanceLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        GridPane.setConstraints(balanceLabel, 0, 0);

        Button viewBalanceButton = new Button("View Balance");
        viewBalanceButton.setOnAction(e -> viewBalance());
        GridPane.setConstraints(viewBalanceButton, 1, 0);

        Label incomeLabel = new Label("Add Income: $");
        incomeLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 14));
        incomeLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        GridPane.setConstraints(incomeLabel, 0, 1);

        incomeTextField = new TextField();
        GridPane.setConstraints(incomeTextField, 1, 1);

        Button addIncomeButton = new Button("Add Income");
        addIncomeButton.setOnAction(e -> addIncome());
        GridPane.setConstraints(addIncomeButton, 2, 1);

        Label expenseLabel = new Label("Add Expense: $");
        expenseLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 14));
        expenseLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        GridPane.setConstraints(expenseLabel, 0, 2);

        expenseTextField = new TextField();
        GridPane.setConstraints(expenseTextField, 1, 2);

        Button addExpenseButton = new Button("Add Expense");
        addExpenseButton.setOnAction(e -> addExpense());
        GridPane.setConstraints(addExpenseButton, 2, 2);

        Button viewSummaryButton = new Button("View Budget Summary");
        viewSummaryButton.setOnAction(e -> viewBudgetSummary());
        GridPane.setConstraints(viewSummaryButton, 1, 3);

        Button homeButton = new Button("Home");
        homeButton.setOnAction(e -> createBankGUI());
        GridPane.setConstraints(homeButton, 2, 3);

        // Set the text area for income and expenses to grow horizontally
        GridPane.setColumnSpan(incomeTextField, 2);
        GridPane.setColumnSpan(expenseTextField, 2);

        grid.getChildren().addAll(
                balanceLabel, viewBalanceButton,
                incomeLabel, incomeTextField, addIncomeButton,
                expenseLabel, expenseTextField, addExpenseButton,
                viewSummaryButton

        );

        return grid;

    }



    private void viewBalance() {
        balanceLabel.setText("Current Balance: $" + balance);
    }

    private void addIncome() {
        try {
            double incomeAmount = Double.parseDouble(incomeTextField.getText());
            income += incomeAmount;
            balance += incomeAmount;
            incomeTextField.clear();
            viewBalance();
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input for income. Please enter a valid number.");
        }
    }

    private void addExpense() {
        try {
            double expenseAmount = Double.parseDouble(expenseTextField.getText());
            if (expenseAmount > balance) {
                showErrorAlert("Insufficient funds. Cannot add expense.");
            } else {
                expenses += expenseAmount;
                balance -= expenseAmount;
                expenseTextField.clear();
                viewBalance();
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input for expense. Please enter a valid number.");
        }
    }

    private void viewBudgetSummary() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Budget Summary");
        alert.setHeaderText(null);
        alert.setContentText("Income: $" + income + "\nExpenses: $" + expenses + "\nRemaining Balance: $" + balance);
        alert.showAndWait();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
    private void createBankGUI(){
        buttonsOverview.createBankGUI(new Stage());
    }
}