package com.example.bnkappcom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

        GridPane grid = createBudgetingGrid();
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createBudgetingGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        balanceLabel = new Label("Current Balance: $");
        GridPane.setConstraints(balanceLabel, 0, 0);

        Button viewBalanceButton = new Button("View Balance");
        viewBalanceButton.setOnAction(e -> viewBalance());
        GridPane.setConstraints(viewBalanceButton, 1, 0);

        Label incomeLabel = new Label("Add Income: $");
        GridPane.setConstraints(incomeLabel, 0, 1);

        incomeTextField = new TextField();
        GridPane.setConstraints(incomeTextField, 1, 1);

        Button addIncomeButton = new Button("Add Income");
        addIncomeButton.setOnAction(e -> addIncome());
        GridPane.setConstraints(addIncomeButton, 2, 1);

        Label expenseLabel = new Label("Add Expense: $");
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
        buttons.createBankGUI(new Stage());
    }
}
