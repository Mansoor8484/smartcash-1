package rebelalliance.smartcash.component.menuitem;

import javafx.scene.control.MenuItem;
import rebelalliance.smartcash.ledger.IDeletable;

public class DeleteMenuItem extends MenuItem {
    public DeleteMenuItem(IDeletable item, Runnable onAction) {
        super("Delete");

        this.setOnAction(e -> {
            item.delete();
            if(onAction != null) {
                onAction.run();
            }
        });
    }
}
