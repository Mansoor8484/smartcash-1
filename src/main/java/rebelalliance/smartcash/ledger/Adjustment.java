package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.util.DateUtil;

import java.time.LocalDate;

public class Adjustment extends LedgerItem {
    public Adjustment(Account account, double amount, String description) {
        this.accountFrom = account;
        this.amount = amount;
        this.notes = description;

        this.date = LocalDate.now();
    }

    public Adjustment(Account account, double amount, String description, LocalDate date) {
        this.accountFrom = account;
        this.amount = amount;
        this.notes = description;

        this.date = date;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": Set " + this.accountFrom + " to $" + this.amount + ".";
    }
}
