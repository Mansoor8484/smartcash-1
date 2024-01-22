package rebelalliance.smartcash.ledger;

import java.util.Collections;
import java.util.List;

public class Ledger {
    List<LedgerItem> ledger;
    List<Account> accounts;
    List<Category> categories;

    public Ledger() {
        this.ledger = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.categories = new ArrayList<>();
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        account.setLedger(this);
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }
}
