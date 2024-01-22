package rebelalliance.smartcash.account;

import rebelalliance.smartcash.ledger.Ledger;

import java.util.Date;
import java.util.UUID;

public class Account {
    private Ledger ledger;

    private String name;
    private UUID uuid;

    private Date creation;

    public Account(String name) {
        this.name = name;
        this.balance = 0.0;
        this.uuid = UUID.randomUUID();
    }

    public Account(String name, UUID uuid) {
        this.name = name;
        this.balance = 0.0;
        this.uuid = uuid;
    }

    @Override
    public void calculate() {
        // TODO: Implement.
    }

    public String getName() {
        return this.name;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Date getCreation() {
        return creation;
    }
}
