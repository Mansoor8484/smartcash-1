package rebelalliance.smartcash.controller.scene;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.database.User;
import rebelalliance.smartcash.scene.SCScene;

public class LoginController extends BaseController implements IController {
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    protected void onLoginClick() {
        User user = this.databaseManager.login(emailInput.getText(), passwordInput.getText());
        if(user == null) {
            // Show error.
            return;
        }

        if(user.hasMfaEnabled()) {
            Stage popup;
            Scene scene;
            popup = new Stage();
            popup.setResizable(false);
            popup.setTitle("2FA");
            VBox root = new VBox();
            root.setSpacing(5);
            root.setPadding(new Insets(5));
            root.setAlignment(Pos.CENTER);
            Text prompt = new Text("Enter your 2FA code:");
            TextField inputBox = new TextField();
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> popup.close());
            root.getChildren().addAll(prompt, inputBox, submitButton);
            scene = new Scene(root, 200, 100);
            popup.setScene(scene);
            popup.showAndWait();

            TimeProvider timeProvider = new SystemTimeProvider();
            CodeGenerator codeGenerator = new DefaultCodeGenerator();
            CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
            boolean successful = verifier.isValidCode(user.getMfaSecret(), inputBox.getText());
            if(!successful) {
                // TODO: Show error.
                return;
            }
        }

        this.sceneManager.setLoggedInUser(user);
        this.sceneManager.setScene(SCScene.OVERVIEW);
    }

    @FXML
    protected void onRegisterClick() {
        this.sceneManager.setScene(SCScene.REGISTER);
    }
}
