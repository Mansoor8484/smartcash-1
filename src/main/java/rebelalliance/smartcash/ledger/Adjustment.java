package rebelalliance.smartcash.ledger;

import java.util.Date;

public class Adjustment extends LedgerItem {
    protected final Date date;

    public Adjustment(String description, double amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }
}
