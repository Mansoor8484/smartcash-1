package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.ledger.IDeletable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Category extends Container implements IDeletable {
    private final List<Category> subcategories;

    public Category(String name) {
        super(name);

        this.subcategories = new ArrayList<>();
    }

    public Category(String name, UUID uuid) {
        super(name, uuid);

        this.subcategories = new ArrayList<>();
    }

    @Override
    public void delete() {
        this.ledger.delete(this);
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

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories.clear();
        this.subcategories.addAll(subcategories);
    }
}
