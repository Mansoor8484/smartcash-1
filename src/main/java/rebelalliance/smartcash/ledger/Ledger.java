package rebelalliance.smartcash.ledger;

import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ledger {
    List<LedgerItem> ledger;
    List<Account> accounts;
    List<Category> categories;

    public Ledger() {
        this.ledger = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.categories = new ArrayList<>();

        // Add default accounts.
        this.addAccount(new Account("Checking"));
        this.addAccount(new Account("Savings"));

        // Add default categories.
        this.addCategory(new Category("Bill"));
        this.addCategory(new Category("Entertainment"));
        this.addCategory(new Category("Food"));
        this.addCategory(new Category("Income"));
        this.addCategory(new Category("Subscription"));
        this.addCategory(new Category("Travel"));

        // TODO: Remove this.
        // Add default ledger items at test code.
        this.ledger.add(new Adjustment(
                this.getAccount("Savings"),
                10000,
                "Initial deposit.",
                LocalDate.parse("2024-01-01")
        ));
        this.ledger.add(new Adjustment(
                this.getAccount("Checking"),
                0,
                "Initial deposit.",
                LocalDate.parse("2024-01-01")
        ));
        this.ledger.add(new Transaction(
                500,
                this.getAccount("Checking"),
                this.getCategory("Income"),
                LocalDate.parse("2024-01-02")
        ));
        this.ledger.add(new Transfer(
                200,
                this.getAccount("Checking"),
                this.getAccount("Savings"),
                LocalDate.parse("2024-01-03")
        ));
        Transaction transactionWithDescription = new Transaction(
                -10,
                this.getAccount("Checking"),
                this.getCategory("Food"),
                LocalDate.parse("2024-01-04")
        );
        transactionWithDescription.setNotes("Bought a sandwich.");
        this.ledger.add(transactionWithDescription);
    }

    public Ledger(List<LedgerItem> ledgerItems) {
        this.ledger = ledgerItems;
    }

    public void sort() {
        this.ledger.sort(Comparator.comparing(LedgerItem::getDate));
    }

    public List<LedgerItem> getLedger() {
        return this.ledger;
    }

    public void add(LedgerItem ledgerItem) {
        this.ledger.add(ledgerItem);
        this.ledger.sort(Comparator.comparing(LedgerItem::getDate));
    }

    public void remove(LedgerItem ledgerItem) {
        this.ledger.remove(ledgerItem);
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
        Account existingAccount = this.getAccount(account.getName());
        if(existingAccount != null) {
            throw new IllegalArgumentException("Account already exists.");
        }

        account.setLedger(this);
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
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

        Category existingCategory = this.getCategory(category.getName());
        if(existingCategory != null) {
            throw new IllegalArgumentException("Category already exists.");
        }

        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
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
        for(LedgerItem ledgerItem : this.ledger) {
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
