package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountCreationModalController {
    Stage stage;

    @FXML
    private TextField accountNameInput;

    private boolean shouldSave = false;

    public void save() {
        shouldSave = true;
        stage.close();
    }

    public String getAccountName() {
        return accountNameInput.getText();
    }

    public boolean shouldSave() {
        return this.shouldSave;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
