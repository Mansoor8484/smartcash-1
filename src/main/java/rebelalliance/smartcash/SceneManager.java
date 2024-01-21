package rebelalliance.smartcash;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rebelalliance.smartcash.controller.BaseController;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    Stage stage;

    private final HashMap<String, Scene> scenes = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setScene(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path + ".fxml"));

        Scene scene = scenes.get(path);
        if(scene == null) {
            try {
                Parent parent = loader.load();
                scene = new Scene(parent);

                BaseController baseController = loader.getController();
                baseController.setSceneManager(this);

                scenes.put(path, scene);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.stage.setScene(scene);
    }
}
