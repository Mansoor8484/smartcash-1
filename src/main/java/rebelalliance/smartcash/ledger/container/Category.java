package rebelalliance.smartcash.ledger.container;

import rebelalliance.smartcash.ledger.IDeletable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Category extends Container implements IDeletable {
    private Category supercategory;
    private final List<Category> subcategories;

    public Category(String name) {
        super(name);

        this.supercategory = null;
        this.subcategories = new ArrayList<>();
    }

    public Category(String name, UUID uuid) {
        super(name, uuid);

        this.supercategory = null;
        this.subcategories = new ArrayList<>();
    }

    @Override
    public void delete() {
        if(this.supercategory != null) {
            this.supercategory.removeSubcategory(this);
        }
        for(Category subcategory : this.subcategories) {
            subcategory.setSupercategory(null);
        }
        this.ledger.delete(this);
    }

    public void addSubcategory(Category category) {
        if(category.equals(this)) {
            throw new IllegalArgumentException("Category cannot be a subcategory of itself.");
        }
        if(category.getSupercategory().equals(this) || category.hasSubcategory(this)) {
            throw new IllegalArgumentException("Recursive categories not allowed.");
        }

        if(category.getSupercategory() != null) {
            category.getSupercategory().removeSubcategory(category);
        }
        this.subcategories.add(category);
        category.setSupercategory(this);
    }

    public void removeSubcategory(Category category) {
        this.subcategories.remove(category);
        category.setSupercategory(null);
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories.clear();
        for(Category category : subcategories) {
            this.addSubcategory(category);
            category.setSupercategory(this);
        }
    }

    public boolean hasSubcategory(Category category) {
        List<Category> subcategories = category.getSubcategories();
        for(Category subcategory : subcategories) {
            if(!subcategory.hasSubcategory(category)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public void setSupercategory(Category supercategory) {
        if(supercategory.equals(this)) {
            throw new IllegalArgumentException("Category cannot be a subcategory of itself.");
        }
        if(supercategory.getSupercategory().equals(this) || supercategory.hasSubcategory(this)) {
            throw new IllegalArgumentException("Recursive categories not allowed.");
        }

        if(this.supercategory != null) {
            this.supercategory.removeSubcategory(this);
        }
        this.supercategory = supercategory;
        supercategory.addSubcategory(this);
    }

    public List<Category> getSubcategories() {
        return this.subcategories;
    }

    public Category getSupercategory() {
        return this.supercategory;
    }
}
