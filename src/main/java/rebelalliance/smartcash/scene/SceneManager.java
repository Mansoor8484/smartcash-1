package rebelalliance.smartcash.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.database.DatabaseManager;
import rebelalliance.smartcash.file.preferences.UserPreferences;
import rebelalliance.smartcash.ledger.Ledger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneManager {
    Stage stage;
    private final DatabaseManager databaseManager;

    private final HashMap<SCScene, Scene> scenes = new HashMap<>();
    private final HashMap<SCScene, BaseController> controllers = new HashMap<>();
    private final Ledger ledger;

    private final UserPreferences userPreferences;

    private SCScene currentScene;

    public SceneManager(Stage stage, DatabaseManager databaseManager) {
        this.stage = stage;
        this.databaseManager = databaseManager;

        // TODO: Move this.
        this.ledger = new Ledger();

        this.userPreferences = new UserPreferences("smartcash");
    }

    public void setScene(SCScene path) {
        if(this.currentScene == path) {
            return;
        }

        this.currentScene = path;

        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/scene/" + path.getPath() + ".fxml"));

        Scene scene = scenes.get(path);
        if(scene == null) {
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);
                URL stylesheetUrl = SmartCash.class.getResource("css/" + path.getPath() + ".css");
                if(stylesheetUrl != null) {
                    scene.getStylesheets().add(stylesheetUrl.toExternalForm());
                }
                scene.getRoot().setStyle("-fx-background-color: #3b3b3b");

                BaseController baseController = loader.getController();
                baseController.setSceneManager(this);
                baseController.setDatabaseManager(this.databaseManager);
                baseController.setUserPreferences(this.userPreferences);
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
        return this.ledger;
    }

    public UserPreferences getUserPreferences() {
        return this.userPreferences;
    }
}
