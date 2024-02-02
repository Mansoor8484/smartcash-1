package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.ledger.IDeletable;
import rebelalliance.smartcash.ledger.Ledger;

import java.util.ArrayList;
import java.util.List;

public class Category implements IArchivable, IDeletable {
    private Ledger ledger;

    private final String name;
    private final List<Category> subcategories;
    private boolean isArchived;

    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
        this.isArchived = false;
    }

    public Category(String name, List<Category> subcategories) {
        this.name = name;
        this.subcategories = subcategories;
        this.isArchived = false;
    }

    public void delete() {
        this.ledger.delete(this);
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public String getName() {
        return this.name;
    }

    public List<Category> getSubcategories() {
        return this.subcategories;
    }

    public void addSubcategory(Category category) {
        this.subcategories.add(category);
    }

    public void removeSubcategory(Category category) {
        this.subcategories.remove(category);
    }

    public boolean isArchived() {
        return this.isArchived;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories.clear();
        this.subcategories.addAll(subcategories);
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String toString() {
        return this.name;
    }
}
