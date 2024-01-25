package rebelalliance.smartcash.ledger.transaction.recurring;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.transaction.Transaction;

import java.time.LocalDate;

public class RecurringTransaction extends Transaction {
    protected Frequency frequency;

    public RecurringTransaction(double amount, Account to, Category category, Frequency frequency) {
        super(amount, to, category);

        this.frequency = frequency;
    }

    public RecurringTransaction(double amount, Account to, Category category, LocalDate date, Frequency frequency) {
        super(amount, to, category, date);

        this.frequency = frequency;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
}
