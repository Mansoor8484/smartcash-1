package rebelalliance.smartcash.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.Ledger;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    Stage stage;

    private final HashMap<SCScene, Scene> scenes = new HashMap<>();
    private final Ledger ledger;

    public SceneManager(Stage stage) {
        this.stage = stage;

        // TODO: Move this.
        this.ledger = new Ledger();

        // Test code.
        // TODO: Remove this.
        Account account1 = new Account("Test Account 1");
        Account account2 = new Account("Test Account 2");
        Category category = new Category("Test Category");
        this.ledger.addAccount(account1);
        this.ledger.addAccount(account2);
        this.ledger.addCategory(category);
        Adjustment adjustment1 = new Adjustment(account1, 532.24, "Initial deposit.");
        this.ledger.add(adjustment1);
    }

    public void setScene(SCScene path) {
        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/" + path.getPath() + ".fxml"));

        Scene scene = scenes.get(path);
        if(scene == null) {
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);

                BaseController baseController = loader.getController();
                baseController.setSceneManager(this);
                baseController.init();

                scenes.put(path, scene);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.stage.setScene(scene);
    }

    public Ledger getLedger() {
        return ledger;
    }
}
