package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Category;

import java.util.List;

public class TransactionModalController {
    Stage stage;

    boolean shouldSave = false;

    @FXML
    private TextField amountInput;
    @FXML
    private ComboBox<Account> accountFromInput;
    @FXML
    private ComboBox<Category> categoryInput;

    public void save() {
        shouldSave = true;
        stage.close();
    }

    public boolean shouldSave() {
        return shouldSave;
    }

    public double getAmount() {
        return Double.parseDouble(amountInput.getText());
    }

    public Account getAccountFrom() {
        return accountFromInput.getValue();
    }

    public Category getCategory() {
        return categoryInput.getValue();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAccountOptions(List<Account> accounts) {
        accountFromInput.getItems().addAll(accounts);
    }

    public void setCategoryOptions(List<Category> categories) {
        categoryInput.getItems().addAll(categories);
    }
}
