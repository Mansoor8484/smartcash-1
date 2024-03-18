package rebelalliance.smartcash.controller.scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.util.EmailUtil; 
import java.util.Random;
import rebelalliance.smartcash.database.DatabaseConnections.src.AzureDatabaseConnection;


public class RegisterController extends BaseController implements IController{
    Random random = new Random();
    AzureDatabaseConnection databaseConnection = new AzureDatabaseConnection();

    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private VBox errors;
    @FXML
    private Button registerButton;

    @Override
    public void init() {
    }

    @Override
    public void update() {
    }

    private Text getErrorText(String text) {
        Text errorText = new Text(text);
        errorText.setStyle("-fx-fill: #ff0000");

        return errorText;
    }

    public void onEmailChange() {
        this.registerButton.setDisable(false);
    }

    public void onPasswordChange() {
        this.registerButton.setDisable(false);
    }

    @FXML
    protected void onRegisterClick(){
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
        // ...

            int accountNumber = random.nextInt(100000, 1000000);
            int routingNumber = random.nextInt(100000, 1000000);

            // TODO: Remove this.
  

            // TODO: Call database function.
    
            this.databaseManager.registerUser(email, password);

            //Have to call the database function to register the user along with creating their user number and routing and account number
            this.databaseConnection.DBregisterUser(email, password);

            // Show success prompt.
            // TODO: Remove this.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account Created");
        alert.setContentText("Your account has been created. You may log in now.");
        alert.showAndWait();

        // Go to login.
        this.sceneManager.setScene(SCScene.LOGIN);
    }

    @FXML
    protected void onLoginClick() {
        this.sceneManager.setScene(SCScene.LOGIN);
    }

}
