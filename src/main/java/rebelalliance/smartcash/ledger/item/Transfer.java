package rebelalliance.smartcash.ledger.item;

import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

import java.time.LocalDate;

public class Transfer extends LedgerItem {
    protected final Account accountTo;

    public Transfer(double amount, Account accountFrom, Account accountTo) {
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;

        this.date = LocalDate.now();
    }

    public Transfer(double amount, Account accountFrom, Account accountTo, LocalDate date) {
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;

        this.date = date;
    }

    public Account getAccountTo() {
        return this.accountTo;
    }

    public String toString() {
        return DateUtil.format(this.date) + ": Transferred " + NumberUtil.formatAsAmount(this.amount) + " from " + this.accountFrom + " to " + this.accountTo + ".";
    }
}
