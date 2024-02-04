package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.exception.NotImplementedException;
import rebelalliance.smartcash.ledger.IDeletable;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.item.Adjustment;
import rebelalliance.smartcash.ledger.item.LedgerItem;
import rebelalliance.smartcash.ledger.item.transaction.Transaction;
import rebelalliance.smartcash.ledger.item.Transfer;
import rebelalliance.smartcash.util.MathUtil;

import java.time.LocalDate;
import java.util.UUID;

public class Account implements IArchivable, IDeletable {
    private Ledger ledger;

    private String name;
    private final UUID uuid;
    private boolean isArchived;

    public Account(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.isArchived = false;
    }

    public Account(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        this.isArchived = false;
    }

    public void delete() {
        this.ledger.delete(this);
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
        for(LedgerItem ledgerItem : this.ledger.getItems()) {
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

    public boolean isArchived() {
        return this.isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String toString() {
        return this.name;
    }
}
