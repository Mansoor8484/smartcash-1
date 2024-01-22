package rebelalliance.smartcash.controller;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.transaction.Transaction;

public class TransactionsController extends BaseController {
    Ledger ledger = new Ledger();
    Account account1 = new Account("Test Account 1");
    Account account2 = new Account("Test Account 2");
    Category category = new Category("Test Category");

    boolean hasInitialized = false;

    public void addTestTransaction() {
        if(!this.hasInitialized) {
            this.ledger.addAccount(this.account1);
            this.ledger.addAccount(this.account2);
            this.ledger.addCategory(this.category);

            Adjustment adjustment1 = new Adjustment(account1, 532.24, "Income");
            ledger.add(adjustment1);

            this.hasInitialized = true;
        }

        Transaction transaction = new Transaction(
                100.0,
                account1,
                account2,
                category
        );
        ledger.add(transaction);

        System.out.println(ledger);
    }
}
