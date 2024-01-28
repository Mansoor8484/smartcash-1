package rebelalliance.smartcash.statistic;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.Transfer;
import rebelalliance.smartcash.ledger.transaction.Transaction;

import java.time.LocalDate;
import java.util.HashMap;

public class LedgerStats {
    Ledger ledger;

    private HashMap<LocalDate, HashMap<Account, Offset>> offsets = new HashMap<>();

    public LedgerStats(Ledger ledger) {
        this.ledger = ledger;
    }

    HashMap<Account, Offset> getDayOffsets(LocalDate date) {
        return this.offsets.get(date);
    }

    public HashMap<LocalDate, HashMap<Account, Offset>> generateOffsets() {
        // Generate a list of offsets for each date in the ledger.
        for(LedgerItem ledgerItem : this.ledger.getLedger()) {
            if(!this.offsets.containsKey(ledgerItem.getDate())) {
                this.offsets.put(ledgerItem.getDate(), new HashMap<>());
            }
            HashMap<Account, Offset> dayOffsets = this.getDayOffsets(ledgerItem.getDate());

            // Add account "from" offset if it doesn't exist.
            if(!dayOffsets.containsKey(ledgerItem.getAccountFrom())) {
                dayOffsets.put(ledgerItem.getAccountFrom(), new Offset());
            }

            if(ledgerItem instanceof Adjustment adjustment) {
                Offset offset = dayOffsets.get(adjustment.getAccountFrom());

                double original = ledgerItem.getAccountFrom().getBalanceTo(ledgerItem.getDate().minusDays(1));
                double difference = adjustment.getAmount() - original;
                offset.offsetOffset(difference);

                continue;
            }

            if(ledgerItem instanceof Transaction transaction) {
                Offset offset = dayOffsets.get(transaction.getAccountFrom());
                offset.offsetOffset(transaction.getAmount());
                continue;
            }

            if(ledgerItem instanceof Transfer transfer) {
                // Add account "to" offset if it doesn't exist.
                if(!dayOffsets.containsKey(transfer.getAccountTo())) {
                    dayOffsets.put(transfer.getAccountTo(), new Offset());
                }

                Offset offsetFrom = dayOffsets.get(transfer.getAccountFrom());
                offsetFrom.offsetOffset(-transfer.getAmount());

                Offset offsetTo = dayOffsets.get(transfer.getAccountTo());
                offsetTo.offsetOffset(transfer.getAmount());
            }
        }

        return this.offsets;
    }

    public HashMap<LocalDate, HashMap<Account, Double>> getBalanceDayOverDay() {
        HashMap<LocalDate, HashMap<Account, Double>> balanceDayOverDay = new HashMap<>();

        HashMap<Account, Double> runningBalances = new HashMap<>();

        LocalDate[] dates = this.offsets.keySet().stream().sorted().toArray(LocalDate[]::new);
        for(LocalDate date : dates) {
            if(!balanceDayOverDay.containsKey(date)) {
                balanceDayOverDay.put(date, new HashMap<>());
            }
            for(Account account : this.offsets.get(date).keySet()) {
                if(!runningBalances.containsKey(account)) {
                    runningBalances.put(account, 0.0);
                }
                double runningBalance = runningBalances.get(account);
                double dayOffset = this.offsets.get(date).get(account).getOffset();
                runningBalance += dayOffset;
                runningBalances.put(account, runningBalance);
            }

            balanceDayOverDay.put(date, new HashMap<>(runningBalances));
        }

        return balanceDayOverDay;
    }

    public HashMap<LocalDate, HashMap<Account, Offset>> getOffsets() {
        return this.offsets;
    }
}
