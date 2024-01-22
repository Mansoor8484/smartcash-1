package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.util.DateUtil;

import java.util.Date;

public class Adjustment extends LedgerItem {
    protected final Date date;

    public Adjustment(Account account, double amount, String description) {
        this.accountTo = account;
        this.description = description;
        this.amount = amount;

        this.date = new Date();
    }

    public Adjustment(Account account, double amount, String description, Date date) {
        this.accountTo = account;
        this.description = description;
        this.amount = amount;

        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": Set " + this.accountTo + " to $" + this.amount + ".";
    }
}
