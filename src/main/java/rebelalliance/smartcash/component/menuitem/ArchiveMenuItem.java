package rebelalliance.smartcash.component.menuitem;

import javafx.scene.control.MenuItem;
import rebelalliance.smartcash.ledger.container.IArchivable;

public class ArchiveMenuItem extends MenuItem {
    public ArchiveMenuItem(IArchivable item, Runnable onAction) {
        super("Archive");

        this.setOnAction(e -> {
            item.setArchived(true);
            if(onAction != null) {
                onAction.run();
            }
        });
    }
}
