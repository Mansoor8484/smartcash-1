package rebelalliance.smartcash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.scene.SCScene;

import java.util.List;

public class OverviewController extends BaseController implements IController {
    @FXML
    VBox accounts;

    @Override
    public void init() {
        this.update();
    }

    @Override
    public void update() {
        this.accounts.getChildren().clear();

        List<Account> accounts = this.sceneManager.getLedger().getAccounts();
        for(Account account : accounts) {
            Text text = new Text(account + ": $" + account.getBalance());
            this.accounts.getChildren().add(text);
        }
    }

    public void testGoToTransactions(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }
}
