package rebelalliance.smartcash.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rebelalliance.smartcash.controller.BaseController;

public class CategoryCreationModalController extends BaseController {
    Stage stage;

    @FXML
    private TextField categoryNameInput;

    private boolean shouldSave = false;

    public void save() {
        if(categoryNameInput.getText().trim().isEmpty()) {
            return;
        }

        shouldSave = true;
        stage.close();
    }

    public void reject() {
        shouldSave = false;
        this.categoryNameInput.setText("");
    }

    public String getCategoryName() {
        return categoryNameInput.getText();
    }

    public boolean shouldSave() {
        return this.shouldSave;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
