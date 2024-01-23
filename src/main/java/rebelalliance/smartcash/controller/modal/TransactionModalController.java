package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Category;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TransactionModalController {
    Stage stage;

    boolean shouldSave = false;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountInput;
    @FXML
    private ComboBox<Account> accountFromInput;
    @FXML
    private ComboBox<Category> categoryInput;
    @FXML
    private TextField notesInput;

    public void init() {
        this.datePicker.setValue(LocalDate.now());
    }

    public void save() {
        shouldSave = true;
        stage.close();
    }

    public boolean shouldSave() {
        return shouldSave;
    }

    public Date getDate() {
        return Date.from(this.datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    public String getNotes() {
        return notesInput.getText();
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
