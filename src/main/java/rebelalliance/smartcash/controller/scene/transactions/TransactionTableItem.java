package rebelalliance.smartcash.controller.scene.transactions;

import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

class TransactionTableItem {
    String date;
    String amount;
    String account;
    String category;
    String notes;

    public TransactionTableItem(Transaction transaction) {
        this.date = DateUtil.format(transaction.getDate());
        this.amount = NumberUtil.formatAsAmount(transaction.getAmount());
        this.account = transaction.getAccountFrom().getName();
        this.category = transaction.getCategory().getName();
        this.notes = transaction.getNotes();
    }

    public TransactionTableItem(Adjustment adjustment) {
        this.date = DateUtil.format(adjustment.getDate());
        this.amount = NumberUtil.formatAsAmount(adjustment.getAmount());
        this.account = adjustment.getAccountFrom().getName();
        this.category = "Adjustment";
        this.notes = adjustment.getNotes();
    }

    public TransactionTableItem(String date, String amount, String account, String category, String notes) {
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.category = category;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getAccount() {
        return account;
    }

    public String getCategory() {
        return category;
    }

    public String getNotes() {
        return notes;
    }
}