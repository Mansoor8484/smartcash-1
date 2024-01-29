package rebelalliance.smartcash.statistic;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.Transfer;
import rebelalliance.smartcash.ledger.transaction.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class LedgerStats {
    Ledger ledger;

    private final SortedMap<LocalDate, HashMap<Account, List<Offset>>> offsets = new TreeMap<>();

    public LedgerStats(Ledger ledger) {
        this.ledger = ledger;
    }

    HashMap<Account, List<Offset>> getDayOffsets(LocalDate date) {
        return this.offsets.get(date);
    }

    public void generateOffsets() {
        this.ledger.sort();
        this.offsets.clear();

        // Generate a list of offsets for each date in the ledger.
        for(LedgerItem ledgerItem : this.ledger.getLedger()) {
            if(!this.offsets.containsKey(ledgerItem.getDate())) {
                this.offsets.put(ledgerItem.getDate(), new HashMap<>());
            }

            Offset offset;
            HashMap<Account, List<Offset>> dayOffsets = this.offsets.get(ledgerItem.getDate());

            if(!dayOffsets.containsKey(ledgerItem.getAccountFrom())) {
                dayOffsets.put(ledgerItem.getAccountFrom(), new java.util.ArrayList<>());
            }
            List<Offset> fromOffsets = dayOffsets.get(ledgerItem.getAccountFrom());

            if(ledgerItem instanceof Adjustment) {
                offset = new Offset(ledgerItem.getAmount(), OffsetType.ADJUSTMENT);
                fromOffsets.add(offset);
            }else if(ledgerItem instanceof Transaction) {
                offset = new Offset(ledgerItem.getAmount(), OffsetType.LOCAL);
                fromOffsets.add(offset);
            }else if(ledgerItem instanceof Transfer transfer) {
                if(!dayOffsets.containsKey(transfer.getAccountTo())) {
                    dayOffsets.put(transfer.getAccountTo(), new java.util.ArrayList<>());
                }
                List<Offset> toOffsets = dayOffsets.get(transfer.getAccountTo());

                offset = new Offset(transfer.getAmount(), OffsetType.LOCAL);
                toOffsets.add(offset);

                offset = new Offset(-transfer.getAmount(), OffsetType.LOCAL);
                fromOffsets.add(offset);
            }
        }
    }

    public SortedMap<LocalDate, HashMap<Account, Double>> getBalanceDayOverDay() {
        this.generateOffsets();

        SortedMap<LocalDate, HashMap<Account, Double>> balanceDayOverDay = new TreeMap<>();

        HashMap<Account, Double> runningBalances = new HashMap<>();
        for(LocalDate date : this.offsets.keySet()) {
            HashMap<Account, List<Offset>> dayOffsets = this.getDayOffsets(date);
            for(Account account : dayOffsets.keySet()) {
                if(!runningBalances.containsKey(account)) {
                    runningBalances.put(account, 0.0);
                }
                double totalOffset = 0;
                for(Offset offset : dayOffsets.get(account)) {
                    if(offset.offsetType() == OffsetType.LOCAL) {
                        totalOffset += offset.offset();
                    }else if(offset.offsetType() == OffsetType.ADJUSTMENT) {
                        totalOffset += offset.getAdjustmentOffset(runningBalances.get(account));
                    }
                }
                runningBalances.put(account, runningBalances.get(account) + totalOffset);
            }
            balanceDayOverDay.put(date, new HashMap<>(runningBalances));
        }

        return balanceDayOverDay;
    }
}
