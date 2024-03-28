package rebelalliance.smartcash.controller.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;

public class SecurityController extends BaseController implements IController {
    @FXML
    protected Button mfaEnableButton;

    @Override
    public void init() {
        this.addHeader();

        // Update UI if user has MFA enabled.
        if(this.sceneManager.getLoggedInUser().hasMfaEnabled()) {
            this.mfaEnableButton.setText("Disable MFA");
        }
    }

    @FXML
    protected void onMfaButtonClick() {
        if(this.sceneManager.getLoggedInUser().hasMfaEnabled()) {
            // Disable MFA.
        }else {
            // Enable MFA.
        }
    }
}
