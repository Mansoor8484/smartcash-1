package rebelalliance.smartcash.ledger;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private final List<Category> subcategories;

    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
    }

    public Category(String name, List<Category> subcategories) {
        this.name = name;
        this.subcategories = subcategories;
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

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories.clear();
        this.subcategories.addAll(subcategories);
    }

    public String toString() {
        return this.name;
    }
}
