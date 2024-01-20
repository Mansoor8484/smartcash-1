package rebelalliance.smartcash.scene.register;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.util.EmailUtil;

import java.util.Random;

public class RegisterController {
    Random random = new Random();

    public TextField emailInput;
    public PasswordField passwordInput;
    public VBox errors;
    public Button registerButton;

    private Text getErrorText(String text) {
        Text errorText = new Text(text);
        errorText.setStyle("-fx-fill: #ff0000");

        return errorText;
    }

    public void onEmailChange(KeyEvent keyEvent) {
        this.registerButton.setDisable(false);
    }

    public void onPasswordChange(KeyEvent keyEvent) {
        this.registerButton.setDisable(false);
    }

    @FXML
    protected void onRegisterClick() {
        this.errors.getChildren().clear();

        String email = emailInput.getText();
        String password = passwordInput.getText();

        // Validate information.
        boolean isValid = true;
        if(email.isEmpty()) {
            this.errors.getChildren().add(this.getErrorText("Email is required."));
            isValid = false;
        }else if(!EmailUtil.isValidEmail(email)) {
            this.errors.getChildren().add(this.getErrorText("Email is invalid."));
            isValid = false;
        }
        if(password.isEmpty()) {
            this.errors.getChildren().add(this.getErrorText("Password is required."));
            isValid = false;
        }else if(password.length() < 8) {
            this.errors.getChildren().add(this.getErrorText("Password must be at least 8 characters."));
            isValid = false;
        }
        if(!isValid) {
            registerButton.setDisable(true);
            return;
        }

        // These two lines should be moved to the backend.
        int accountNumber = random.nextInt(100000, 1000000);
        int routingNumber = random.nextInt(100000, 1000000);

        // TODO: Remove this.
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Routing Number: " + routingNumber);

        // TODO: Call database function.
    }
}
