package rebelalliance.smartcash.scene;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.UUID;

public class RegisterController {
    public TextField emailInput;
    public PasswordField passwordInput;

    @FXML
    protected void onRegisterClick() {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        UUID uuid = UUID.randomUUID();

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("UUID: " + uuid);

        // TODO: Call database function.
    }
}
