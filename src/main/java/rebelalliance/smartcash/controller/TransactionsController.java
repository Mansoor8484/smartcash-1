package rebelalliance.smartcash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.scene.SCScene;

public class TransactionsController extends BaseController implements IController {
    @FXML
    private VBox vBox;

    @Override
    public void init() {
        this.update();
    }

    @Override
    public void update() {
        vBox.getChildren().clear();

        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getLedger()) {
            Text text = new Text();
            text.setText(ledgerItem.toString());
            vBox.getChildren().add(text);
        }
    }

    public void addTestTransaction() {
        Ledger ledger = this.sceneManager.getLedger();
        Transaction transaction = new Transaction(
                100.0,
                ledger.getAccount("Test Account 1"),
                ledger.getCategory("Test Category")
        );
        this.sceneManager.getLedger().add(transaction);

        this.update();
    }

    public void testGoToOverview(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.OVERVIEW);
    }
}
