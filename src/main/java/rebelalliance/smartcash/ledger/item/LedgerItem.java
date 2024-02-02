package rebelalliance.smartcash.ledger.item;

import rebelalliance.smartcash.ledger.IDeletable;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.container.Account;

import java.time.LocalDate;

public class LedgerItem implements IDeletable {
    protected Account accountFrom;
    protected double amount;
    protected String notes;
    protected LocalDate date;

    protected Ledger ledger;

    public void delete() {
        this.ledger.delete(this);
    }

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

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }
}
