package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.ledger.container.Account;

import java.time.LocalDate;
import java.util.List;

public class TransferModalController extends BaseController {
    Stage stage;

    boolean shouldSave = false;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountInput;
    @FXML
    private ComboBox<Account> accountFromInput;
    @FXML
    private ComboBox<Account> accountToInput;
    @FXML
    private TextField notesInput;

    @Override
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
        if(accountToInput.getValue() == null) {
            return;
        }

        shouldSave = true;
        stage.close();
    }

    public double getAmount() {
        return Double.parseDouble(amountInput.getText());
    }

    public boolean shouldSave() {
        return this.shouldSave;
    }

    public Account getAccountFrom() {
        return accountFromInput.getValue();
    }

    public Account getAccountTo() {
        return accountToInput.getValue();
    }

    public LocalDate getDate() {
        return this.datePicker.getValue();
    }

    public String getNotes() {
        return this.notesInput.getText();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAccountOptions(List<Account> accounts) {
        accountFromInput.getItems().addAll(accounts);
        accountToInput.getItems().addAll(accounts);
    }
}
