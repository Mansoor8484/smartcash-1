package rebelalliance.smartcash.file;

import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.Transfer;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.util.CSVReader;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class SampleData {
    private final String path;
    private final Ledger ledger;

    public SampleData(String path, Ledger ledger) {
        this.path = "data/" + path;
        this.ledger = ledger;
    }

    public void loadSampleData() {
        List<List<String>> data;
        try {
            URL path = SmartCash.class.getResource(this.path);
            if(path == null) {
                throw new FileNotFoundException("File not found: " + this.path);
            }
            data = CSVReader.readCSV(path.toURI());
        }catch(Exception e) {
            e.printStackTrace();
            return;
        }
        for(List<String> row : data) {
            LocalDate date = LocalDate.parse(row.get(0));
            double amount = Double.parseDouble(row.get(1).replaceAll("\\$", ""));
            String accountString = row.get(2);
            String categoryString = row.get(3);
            String notes = null;
            if(row.size() == 5) {
                notes = row.get(4);
            }

            // Create the account if it doesn't exist.
            if(this.ledger.getAccount(accountString) == null) {
                this.ledger.addAccount(new Account(accountString));
            }
            // Create the category if it doesn't exist.
            if(this.ledger.getCategory(categoryString) == null) {
                if(!categoryString.equals("Adjustment") && !categoryString.equals("Transfer")) {
                    this.ledger.addCategory(new Category(categoryString));
                }
            }

            Account account = ledger.getAccount(accountString);

            if(categoryString.equals("Adjustment")) {
                Adjustment adjustment = new Adjustment(account, amount, date);
                adjustment.setNotes(notes);
                this.ledger.add(adjustment);
                continue;
            }
            if(categoryString.equals("Transfer")) {
                String toAccountString = notes.replaceAll("to ", "");
                if(ledger.getAccount(toAccountString) == null) {
                    this.ledger.addAccount(new Account(toAccountString));
                }
                Account accountTo = ledger.getAccount(toAccountString);
                Transfer transfer = new Transfer(Math.abs(amount), account, accountTo, date);
                this.ledger.add(transfer);
                continue;
            }

            Category category = ledger.getCategory(categoryString);
            Transaction transaction = new Transaction(amount, account, category, date);
            transaction.setNotes(notes);
            this.ledger.add(transaction);
        }
    }
}
