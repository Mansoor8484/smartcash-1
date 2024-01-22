package rebelalliance.smartcash.ledger;

import java.util.Collections;
import java.util.List;

public class Ledger {
    List<LedgerItem> ledger;

    public Ledger() {
        this.ledger = Collections.emptyList();
    }

    public Ledger(List<LedgerItem> ledgerItems) {
        this.ledger = ledgerItems;
    }

    public List<LedgerItem> getLedger() {
        return this.ledger;
    }

    public void add(LedgerItem ledgerItem) {
        this.ledger.add(ledgerItem);
    }

    public void remove(LedgerItem ledgerItem) {
        this.ledger.remove(ledgerItem);
    }
}
