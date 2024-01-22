package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;

import java.util.Date;

public class LedgerItem {
    protected Account accountTo;
    protected double amount;
    protected String description;
    protected Date date;

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
