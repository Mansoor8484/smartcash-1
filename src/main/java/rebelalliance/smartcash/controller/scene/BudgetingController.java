package rebelalliance.smartcash.controller.scene;




import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class BudgetingController extends BaseController implements IController {

    @FXML
    private Label balanceLabel;

    @FXML
    private LineChart<Number, Number> budgetLineChart;
    private XYChart.Series<Number, Number> balanceSeries;



    @FXML
    private TextField incomeAmountTextField;

    @FXML
    private TextField incomeDescriptionTextField;

    @FXML
    private TextField expenseAmountTextField;

    @FXML
    private TextField expenseDescriptionTextField;

    @FXML
    private PieChart budgetPieChart; // Declaration for PieChart

    private double balance = 0;
    private double income = 0;
    private double expenses = 0;




    public void initialize() {
        // Initialize balance series
        balanceSeries = new XYChart.Series<>();
        balanceSeries.setName("Balance");
        balanceSeries.getData().add(new XYChart.Data<>(0, 0)); // Initial point at time 0 with balance 0
        budgetLineChart.getData().add(balanceSeries);
    }

    @FXML
    protected void addIncome() {
        try {
            double incomeAmount = Double.parseDouble(incomeAmountTextField.getText());
            String incomeDescription = incomeDescriptionTextField.getText();
            income += incomeAmount;
            balance += incomeAmount;
            incomeAmountTextField.clear();
            incomeDescriptionTextField.clear();
            updateCharts();
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
                updateCharts();
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid input for expense amount. Please enter a valid number.");
        }
    }

    private void updateCharts() {
        // Update balance series
        balanceSeries.getData().add(new XYChart.Data<>(balanceSeries.getData().size(), balance));
    }

    @FXML
    protected void viewBudgetSummary() {
        // Update PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Income", income),
                new PieChart.Data("Expenses", expenses)
        );
        budgetPieChart.setData(pieChartData);



        // Update LineChart
        XYChart.Series<Number, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.getData().add(new XYChart.Data<>(1, 0)); // Start point at time 1 with income 0
        incomeSeries.getData().add(new XYChart.Data<>(2, income)); // End point at time 2 with total income


        XYChart.Series<Number, Number> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Expenses");
        expensesSeries.getData().add(new XYChart.Data<>(1, income)); // Start point at time 1 with total income
        expensesSeries.getData().add(new XYChart.Data<>(2, income - expenses)); // End point at time 2 with remaining balance




        // Show summary
        String summary = "Total Income: $" + income + "\nTotal Expenses: $" + expenses + "\nRemaining Balance: $" + balance;
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
        this.addHeader();
    }
}
