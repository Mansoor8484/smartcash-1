package rebelalliance.smartcash.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;

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
            if(ledgerItem instanceof Transaction transaction) {
                text.setText(transaction.toString());
            }
            if(ledgerItem instanceof Adjustment adjustment) {
                text.setText(adjustment.toString());
            }
            vBox.getChildren().add(text);
        }
    }

    public void addTestTransaction() {
        Ledger ledger = this.sceneManager.getLedger();
        Transaction transaction = new Transaction(
                100.0,
                ledger.getAccount("Test Account 1"),
                ledger.getAccount("Test Account 2"),
                ledger.getCategory("Test Category")
        );
        this.sceneManager.getLedger().add(transaction);

        System.out.println(this.sceneManager.getLedger());

        this.update();
    }
}
