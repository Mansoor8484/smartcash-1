package rebelalliance.smartcash.ledger.transaction;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.LedgerItem;

import java.util.Date;

public class Transaction extends LedgerItem {
    protected Category category;
    protected Account accountFrom;
    protected boolean isComplete;

    public Transaction(double amount, Account accountTo, Account accountFrom, Category category) {
        this.amount = amount;
        this.accountTo = accountTo;
        this.date = new Date();

        this.accountFrom = accountFrom;
        this.category = category;
        this.isComplete = true;
    }

    public Transaction(double amount, Account accountTo, Account accountFrom, Category category, Date date) {
        this.amount = amount;
        this.accountTo = accountTo;
        this.date = date;

        this.category = category;
        this.accountFrom = accountFrom;
        this.isComplete = true;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getFrom() {
        return this.accountFrom;
    }

    public void setFrom(Account from) {
        this.accountFrom = from;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
