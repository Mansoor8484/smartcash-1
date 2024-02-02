package rebelalliance.smartcash.ledger.item;

import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.util.DateUtil;

import java.time.LocalDate;

public class Adjustment extends LedgerItem {
    public Adjustment(Account account, double amount) {
        this.accountFrom = account;
        this.amount = amount;
        this.notes = notes;

        this.date = LocalDate.now();
    }

    public Adjustment(Account account, double amount, LocalDate date) {
        this.accountFrom = account;
        this.amount = amount;
        this.notes = notes;

        this.date = date;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": Set " + this.accountFrom + " to $" + this.amount + ".";
    }
}
