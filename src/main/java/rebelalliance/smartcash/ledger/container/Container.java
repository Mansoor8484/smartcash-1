package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.ledger.Ledger;

import java.util.UUID;

public class Container implements IArchivable {
    protected Ledger ledger;

    protected final UUID uuid;
    protected String name;
    protected boolean isArchived;

    public Container(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.isArchived = false;
    }

    public Container(String name, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
        this.isArchived = false;
    }

    @Override
    public boolean isArchived() {
        return this.isArchived;
    }

    @Override
    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}
