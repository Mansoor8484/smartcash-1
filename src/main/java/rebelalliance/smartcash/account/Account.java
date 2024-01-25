package rebelalliance.smartcash.account;

import rebelalliance.smartcash.exception.NotImplementedException;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.ledger.Transfer;
import rebelalliance.smartcash.util.MathUtil;

import java.time.LocalDate;
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

    public double getBalance(LocalDate dateFrom, LocalDate dateTo) {
        // TODO: Improve this or run on all accounts at the same time, not just one.
        double runningBalance = 0.0;
        for(LedgerItem ledgerItem : this.ledger.getLedger()) {
            if(ledgerItem.getDate().isAfter(dateTo)) {
                break;
            }
            if(ledgerItem.getDate().isBefore(dateFrom)) {
                continue;
            }

            // Handle transactions.
            if(ledgerItem instanceof Transaction transaction) {
                if(transaction.getAccountFrom().equals(this)) {
                    runningBalance += transaction.getAmount();
                }
            }

            // Handle transfers.
            if(ledgerItem instanceof Transfer transfer) {
                if(transfer.getAccountFrom().equals(this)) {
                    runningBalance -= transfer.getAmount();
                }else if(transfer.getAccountTo().equals(this)) {
                    runningBalance += transfer.getAmount();
                }
            }

            // Handle adjustments.
            if(ledgerItem instanceof Adjustment adjustment) {
                if(adjustment.getAccountFrom().equals(this)) {
                    runningBalance = adjustment.getAmount();
                }
            }
        }
        return MathUtil.round(runningBalance, 2);
    }

    public double getBalance() {
        return this.getBalance(
                LocalDate.of(1970, 1, 1),
                LocalDate.now()
        );
    }

    public double getBalanceFrom(LocalDate dateFrom) {
        // TODO: Implement.
        throw new NotImplementedException();
    }

    public double getBalanceTo(LocalDate dateTo) {
        return this.getBalance(LocalDate.of(1970, 1, 1), dateTo);
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
