package rebelalliance.smartcash.ledger.transaction;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

import java.time.LocalDate;

public class Transaction extends LedgerItem {
    protected Category category;
    protected boolean isComplete;

    public Transaction(double amount, Account accountFrom, Category category) {
        this.amount = amount;
        this.date = LocalDate.now();

        this.accountFrom = accountFrom;
        this.category = category;
        this.isComplete = true;
    }

    public Transaction(double amount, Account accountFrom, Category category, LocalDate date) {
        this.amount = amount;
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

    public boolean isComplete() {
        return this.isComplete;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": " + NumberUtil.formatAsAmount(this.amount) + " from " + this.accountFrom + " (" + this.category + ")";
    }
}
