package rebelalliance.smartcash.controller.scene;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;

public class SecurityController extends BaseController implements IController {

    String email = "example@email.com"; // Example email
    String password = "********"; // Example password
    String accountNumber = "1234567890"; // Example account number
    String routingNumber = "987654321"; // Example routing number

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label accountNumberLabel;

    @FXML
    private Label routingNumberLabel;

    @FXML
    private Label accountInfoLabel;

    @FXML
    private Label routingInfoLabel;

    protected void initialize() {


        emailLabel.setText(email);
        passwordLabel.setText(password);
        accountNumberLabel.setText(accountNumber);
        routingNumberLabel.setText(routingNumber);
    }

    @FXML
    protected void showAccountInfo() {
        accountInfoLabel.setVisible(true);
    }

    @FXML
    protected void showRoutingInfo() {
        routingInfoLabel.setVisible(true);
    }

    @Override
    public void init() {
        this.header.getChildren().add(new Navbar(this.sceneManager));
    }
}
