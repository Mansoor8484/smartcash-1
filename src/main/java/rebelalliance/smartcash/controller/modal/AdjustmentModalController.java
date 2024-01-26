package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.controller.BaseController;

import java.time.LocalDate;
import java.util.List;

public class AdjustmentModalController extends BaseController {
    Stage stage;

    boolean shouldSave = false;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountInput;
    @FXML
    private ComboBox<Account> accountFromInput;
    @FXML
    private TextField notesInput;

    public void init() {
        this.datePicker.setValue(LocalDate.now());
    }

    public void save() {
        if(amountInput.getText().isEmpty()) {
            return;
        }
        if(accountFromInput.getValue() == null) {
            return;
        }
        shouldSave = true;
        stage.close();
    }

    public boolean shouldSave() {
        return shouldSave;
    }

    public LocalDate getDate() {
        return this.datePicker.getValue();
    }

    public double getAmount() {
        return Double.parseDouble(amountInput.getText());
    }

    public Account getAccountFrom() {
        return accountFromInput.getValue();
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
}
