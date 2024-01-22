package rebelalliance.smartcash.controller;

import rebelalliance.smartcash.scene.SceneManager;

public class BaseController implements IController {
    SceneManager sceneManager;

    @Override
    public void init() {
    }

    @Override
    public void update() {
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
