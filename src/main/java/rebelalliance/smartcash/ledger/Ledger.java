package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.file.SampleData;
import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.ledger.container.Category;
import rebelalliance.smartcash.ledger.container.Container;
import rebelalliance.smartcash.ledger.item.Adjustment;
import rebelalliance.smartcash.ledger.item.LedgerItem;
import rebelalliance.smartcash.ledger.item.Transfer;
import rebelalliance.smartcash.ledger.item.transaction.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Ledger {
    List<LedgerItem> ledgerItems;
    List<Account> accounts;
    List<Category> categories;

    public Ledger() {
        this.ledgerItems = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.categories = new ArrayList<>();

        // Add default accounts.
        this.addAccount(new Account("Checking"));
        this.addAccount(new Account("Savings"));

        // TODO: Remove this.
        // Load sample data.
        SampleData sampleData = new SampleData("sample.csv", this);
        sampleData.loadSampleData();
    }

    public Ledger(List<LedgerItem> ledgerItems) {
        this.ledgerItems = ledgerItems;
    }

    public void sort() {
        this.ledgerItems.sort(Comparator.comparing(LedgerItem::getDate));
    }

    public List<LedgerItem> getItems() {
        return this.ledgerItems;
    }

    public void add(LedgerItem ledgerItem) {
        ledgerItem.setLedger(this);
        this.ledgerItems.add(ledgerItem);
        this.ledgerItems.sort(Comparator.comparing(LedgerItem::getDate));
    }

    public void remove(LedgerItem ledgerItem) {
        this.ledgerItems.remove(ledgerItem);
    }

    public Container getContainer(String name) {
        for(Account account : this.accounts) {
            if(account.getName().equals(name)) {
                return account;
            }
        }
        for(Category category : this.categories) {
            if(category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String name) {
        for(Account account : this.accounts) {
            if(account.getName().equals(name)) {
                return account;
            }
        }
        return null;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) throws IllegalArgumentException {
        if(this.accountExists(account.getName())) {
            throw new IllegalArgumentException("Account already exists.");
        }

        account.setLedger(this);
        this.accounts.add(account);
    }

    public boolean accountExists(String name) {
        return this.getAccount(name) != null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategory(String name) {
        for(Category category : this.categories) {
            if(category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        if(category.getName().equals("Adjustment")) {
            throw new IllegalArgumentException("Adjustment is a protected category name.");
        }
        if(category.getName().equals("Transfer")) {
            throw new IllegalArgumentException("Transfer is a protected category name.");
        }

        if(this.categoryExists(category.getName())) {
            throw new IllegalArgumentException("Category already exists.");
        }

        category.setLedger(this);
        this.categories.add(category);
    }

    public boolean categoryExists(String name) {
        return this.getCategory(name) != null;
    }

    public void delete(Account account) {
        Iterator<LedgerItem> iterator = this.ledgerItems.iterator();
        while(iterator.hasNext()) {
            LedgerItem ledgerItem = iterator.next();
            if(ledgerItem instanceof Transaction || ledgerItem instanceof Adjustment) {
                if(ledgerItem.getAccountFrom().equals(account)) {
                    iterator.remove();
                    continue;
                }
            }
            if(ledgerItem instanceof Transfer transfer) {
                if(transfer.getAccountFrom().equals(account) || transfer.getAccountTo().equals(account)) {
                    iterator.remove();
                }
            }
        }
        this.accounts.remove(account);
    }

    public void delete(Category category) {
        Iterator<LedgerItem> iterator = this.ledgerItems.iterator();
        while(iterator.hasNext()) {
            LedgerItem ledgerItem = iterator.next();
            if(!(ledgerItem instanceof Transaction transaction)) {
                continue;
            }
            if(!transaction.getCategory().equals(category)) {
                continue;
            }
            iterator.remove();
        }
        this.categories.remove(category);
    }

    public void delete(LedgerItem ledgerItem) {
        this.ledgerItems.remove(ledgerItem);
    }

    public String toString() {
        this.sort();

        StringBuilder result = new StringBuilder();
        double total = 0.0;

        result.append("Ledger\n");
        result.append("========================================\n");
        for(Account account : this.accounts) {
            double balance = account.getBalance();
            total += balance;
            String entry = account.getName() + ": $" + balance;
            result.append(entry).append("\n");
        }
        result.append("----------------------------------------\n");
        result.append("Total: $").append(total).append("\n");
        result.append("========================================\n");
        for(LedgerItem ledgerItem : this.ledgerItems) {
            if(ledgerItem instanceof Adjustment adjustment) {
                result.append(adjustment).append("\n");
            }
            if(ledgerItem instanceof Transaction transaction) {
                result.append(transaction).append("\n");
            }
        }
        result.append("========================================\n");

        return result.toString();
    }
}
