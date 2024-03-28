package rebelalliance.smartcash.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.database.DatabaseManager;
import rebelalliance.smartcash.file.preferences.UserPreferences;
import rebelalliance.smartcash.scene.SceneManager;

public class BaseController implements IController {
    protected SceneManager sceneManager;
    protected UserPreferences userPreferences;
    protected DatabaseManager databaseManager;

    @FXML
    protected VBox header;

    @Override
    public void init() {
    }

    @Override
    public void update() {
    }

    public void addHeader() {
        if(this.header != null) {
            this.header.getChildren().add(new Navbar(this.sceneManager));
        }
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }
}
