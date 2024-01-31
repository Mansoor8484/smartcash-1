package rebelalliance.smartcash.statistic;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.*;
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
                offset = new Offset(ledgerItem.getAmount(), OffsetType.TRANSACTION);
                fromOffsets.add(offset);
            }else if(ledgerItem instanceof Transfer transfer) {
                if(!dayOffsets.containsKey(transfer.getAccountTo())) {
                    dayOffsets.put(transfer.getAccountTo(), new java.util.ArrayList<>());
                }
                List<Offset> toOffsets = dayOffsets.get(transfer.getAccountTo());

                offset = new Offset(transfer.getAmount(), OffsetType.TRANSFER);
                toOffsets.add(offset);

                offset = new Offset(-transfer.getAmount(), OffsetType.TRANSFER);
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
                    if(offset.offsetType() == OffsetType.TRANSACTION || offset.offsetType() == OffsetType.TRANSFER) {
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

    public HashMap<Category, Double> getCategorySpend(LocalDate start, LocalDate end) {
        HashMap<Category, Double> categorySpend = new HashMap<>();
        for(LedgerItem ledgerItem : this.ledger.getLedger()) {
            System.out.println(ledgerItem);
            if(ledgerItem instanceof Adjustment || ledgerItem instanceof Transfer) {
                continue;
            }
            Transaction transaction = (Transaction) ledgerItem;
            if(ledgerItem.getDate().isBefore(start) || ledgerItem.getDate().isAfter(end)) {
                continue;
            }
            if(!categorySpend.containsKey(transaction.getCategory())) {
                categorySpend.put(transaction.getCategory(), 0.0);
            }
            categorySpend.put(transaction.getCategory(), categorySpend.get(transaction.getCategory()) + transaction.getAmount());
        }
        return categorySpend;
    }

    public HashMap<Category, Double> getCategorySpend() {
        return this.getCategorySpend(this.ledger.getLedger().get(0).getDate(), LocalDate.now());
    }
}
