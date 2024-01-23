package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.util.DateUtil;

import java.util.Date;

public class Adjustment extends LedgerItem {
    protected final Date date;

    public Adjustment(Account account, double amount, String description) {
        this.accountFrom = account;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    public Adjustment(Account account, double amount, String description, Date date) {
        this.accountFrom = account;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": Set " + this.accountFrom + " to $" + this.amount + ".";
    }
}
