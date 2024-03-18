package rebelalliance.smartcash.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.scene.SceneManager;

import java.util.Optional;

public class Navbar extends HBox {
    SceneManager sceneManager;

    public Navbar(SceneManager sceneManager) {
        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/component/navbar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }

        this.sceneManager = sceneManager;

        this.setStyle("-fx-background-color: #777777");
        this.setEffect(new DropShadow(
                10,
                0,
                3,
                new Color(0, 0, 0, 0.3)
        ));
    }

    @FXML
    protected void onOverviewClick() {
        this.sceneManager.setScene(SCScene.OVERVIEW);
    }

    @FXML
    protected void onTransactionsClick() {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }

    @FXML
    protected void onBudgetingClick() {
        this.sceneManager.setScene(SCScene.BUDGETING);
    }

    @FXML
    protected void onSecurityClick() {
        this.sceneManager.setScene(SCScene.SECURITY);
    }

    @FXML
    protected void onNotificationSettingsClick() {
        // create a dialog to let the user choose between SMS and email notifications
        Dialog notificationChoice = new Dialog<>();
        notificationChoice.setTitle("Notification Settings");
        notificationChoice.setHeaderText("How do you want to receive notifications?");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        notificationChoice.getDialogPane().getButtonTypes().addAll(cancelButton, okButton);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Preferred Notification Method:"), 0, 0);

        ChoiceBox notificationMethod = new ChoiceBox<>();
        notificationMethod.getItems().addAll("SMS", "Email");
        grid.add(notificationMethod, 1, 0);

        notificationChoice.getDialogPane().setContent(grid);
        notificationChoice.getDialogPane().getButtonTypes().remove(ButtonType.OK);


        Optional result = notificationChoice.showAndWait();
        if(result.isPresent() && result.get() != null) {

            // user selected notification method, now show confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Notification Change");
            alert.setHeaderText("Are you sure you want to change your notification settings?");
            alert.setContentText("You will not be able to revert this action.");

            Optional confirmationResult = alert.showAndWait();
            if(confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                // user clicked OK, so proceed with changing notification settings based on their selection in the previous dialog
                Object selectedMethod = notificationMethod.getValue();
                if(selectedMethod == null) {
                    // user didn't make selection, so do nothing or show an error message as appropriate for your application
                }else {
                    // implement  logic  for changing email or SMS notifications based on user selection
                }
            }else {
                // user clicked Cancel or closed dialog box, so do nothing or show an error message as appropriate for your application
            }
        }
    }

    @FXML
    protected void onActivityClick() {
    }

    @FXML
    protected void onSignOutClick() {
        this.sceneManager.setScene(SCScene.LOGIN);
    }
}
