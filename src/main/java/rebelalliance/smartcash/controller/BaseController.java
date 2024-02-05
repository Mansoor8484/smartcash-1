package rebelalliance.smartcash.controller;

import rebelalliance.smartcash.file.preferences.UserPreferences;
import rebelalliance.smartcash.scene.SceneManager;

public class BaseController implements IController {
    protected SceneManager sceneManager;
    protected UserPreferences userPreferences;

    @Override
    public void init() {
    }

    @Override
    public void update() {
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }
}
