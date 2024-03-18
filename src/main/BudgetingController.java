package rebelalliance.smartcash.controller.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;


public class BudgetingController extends BaseController implements IController {
    @FXML
    private Label balanceLabel;

    @FXML
    private TextField incomeAmountTextField;

    @FXML
    private TextField incomeDescriptionTextField;

    @FXML
    private TextField expenseAmountTextField;

    @FXML
    private TextField expenseDescriptionTextField;

    private double balance = 0;
    private double income = 0;
    private double expenses = 0;

    @FXML
    protected void addIncome() {
        try {
            double incomeAmount = Double.parseDouble(incomeAmountTextField.getText());
            String incomeDescription = incomeDescriptionTextField.getText();
            income += incomeAmount;
            balance += incomeAmount;
            incomeAmountTextField.clear();
            incomeDescriptionTextField.clear();
            updateBalanceLabel();
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input for income amount. Please enter a valid number.");
        }
    }

    @FXML
    protected void addExpense() {
        try {
            double expenseAmount = Double.parseDouble(expenseAmountTextField.getText());
            String expenseDescription = expenseDescriptionTextField.getText();
            if (expenseAmount > balance) {
                showErrorAlert("Insufficient funds. Cannot add expense.");
            } else {
                expenses += expenseAmount;
                balance -= expenseAmount;
                expenseAmountTextField.clear();
                expenseDescriptionTextField.clear();
                updateBalanceLabel();
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input for expense amount. Please enter a valid number.");
        }
    }

    @FXML
    protected void viewBudgetSummary() {
        String summary = "Income: $" + income + "\nExpenses: $" + expenses + "\nRemaining Balance: $" + balance;
        showAlert(Alert.AlertType.INFORMATION, "Budget Summary", summary);
    }

    protected void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: $" + balance);
    }

    protected void showErrorAlert(String errorMessage) {
        showAlert(Alert.AlertType.ERROR, "Error", errorMessage);
    }

    protected void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @Override
    public void init() {
        this.header.getChildren().add(new Navbar(this.sceneManager));
    }
}





