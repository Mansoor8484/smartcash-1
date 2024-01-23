package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;

import java.util.Date;

public class LedgerItem {
    protected Account accountFrom;
    protected double amount;
    protected String notes;
    protected Date date;

    public Account getAccountFrom() {
        return this.accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
