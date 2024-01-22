package rebelalliance.smartcash.account;

import rebelalliance.smartcash.exception.NotImplementedException;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.util.MathUtil;

import java.util.Date;
import java.util.UUID;

public class Account {
    private Ledger ledger;

    private String name;
    private final UUID uuid;

    private Date creation;

    public Account(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public Account(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance(Date dateFrom, Date dateTo) {
        // TODO: Improve this or run on all accounts at the same time, not just one.
        double runningBalance = 0.0;
        for(LedgerItem ledgerItem : this.ledger.getLedger()) {
            if(ledgerItem.getDate().after(dateTo)) {
                break;
            }
            if(ledgerItem.getDate().before(dateFrom)) {
                continue;
            }

            // Handle transactions.
            if(ledgerItem instanceof Transaction transaction) {
                if(transaction.getAccountFrom().equals(this)) {
                    runningBalance -= transaction.getAmount();
                }else if(transaction.getAccountTo().equals(this)) {
                    runningBalance += transaction.getAmount();
                }
            }

            // Handle adjustments.
            if(ledgerItem instanceof Adjustment adjustment) {
                if(adjustment.getAccountTo().equals(this)) {
                    runningBalance = adjustment.getAmount();
                }
            }
        }
        return MathUtil.round(runningBalance, 2);
    }

    public double getBalance() {
        return this.getBalance(new Date(0), new Date());
    }

    public double getBalanceFrom(Date dateFrom) {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    public double getBalanceTo(Date dateTo) {
        return this.getBalance(new Date(0), dateTo);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Date getCreation() {
        return creation;
    }

    public String toString() {
        return this.name;
    }
}
