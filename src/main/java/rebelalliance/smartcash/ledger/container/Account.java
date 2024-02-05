package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.exception.NotImplementedException;
import rebelalliance.smartcash.ledger.IDeletable;
import rebelalliance.smartcash.ledger.item.Adjustment;
import rebelalliance.smartcash.ledger.item.LedgerItem;
import rebelalliance.smartcash.ledger.item.transaction.Transaction;
import rebelalliance.smartcash.ledger.item.Transfer;
import rebelalliance.smartcash.util.MathUtil;

import java.time.LocalDate;
import java.util.UUID;

public class Account extends Container implements IDeletable {
    public Account(String name) {
        super(name);
    }

    public Account(String name, UUID uuid) {
        super(name, uuid);
    }

    @Override
    public void delete() {
        this.ledger.delete(this);
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
}
