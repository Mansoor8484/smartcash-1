package rebelalliance.smartcash.controller.scene;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.scene.SCScene;

public class LoginController extends BaseController implements IController {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    protected void onLoginClick() {
        boolean isLoggedIn = this.databaseManager.isLoginSuccessful(usernameInput.getText(), passwordInput.getText());

        if(isLoggedIn) {
            this.sceneManager.setScene(SCScene.OVERVIEW);
        }
    }

    @FXML
    protected void onRegisterClick() {
        this.sceneManager.setScene(SCScene.REGISTER);
    }
}
