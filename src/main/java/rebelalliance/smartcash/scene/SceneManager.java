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
import rebelalliance.smartcash.ledger.Transfer;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    Stage stage;

    private final HashMap<SCScene, Scene> scenes = new HashMap<>();
    private final HashMap<SCScene, BaseController> controllers = new HashMap<>();
    private final Ledger ledger;

    public SceneManager(Stage stage) {
        this.stage = stage;

        // TODO: Move this.
        this.ledger = new Ledger();
    }

    public void setScene(SCScene path) {
        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/scene/" + path.getPath() + ".fxml"));

        Scene scene = scenes.get(path);
        if(scene == null) {
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);

                BaseController baseController = loader.getController();
                baseController.setSceneManager(this);
                baseController.init();

                scenes.put(path, scene);
                controllers.put(path, baseController);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }else {
            controllers.get(path).update();
        }

        this.stage.setScene(scene);
        this.stage.setTitle(path.getTitle() + " - SmartCash");
    }

    public Ledger getLedger() {
        return ledger;
    }
}
