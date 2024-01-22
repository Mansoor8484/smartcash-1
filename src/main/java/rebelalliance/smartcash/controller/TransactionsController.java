package rebelalliance.smartcash.controller;

import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.transaction.Transaction;

public class TransactionsController extends BaseController {
    public void addTestTransaction() {
        Ledger ledger = this.sceneManager.getLedger();
        Transaction transaction = new Transaction(
                100.0,
                ledger.getAccounts().get(0),
                ledger.getAccounts().get(1),
                ledger.getCategories().get(0)
        );
        this.sceneManager.getLedger().add(transaction);

        System.out.println(this.sceneManager.getLedger());
    }
}
