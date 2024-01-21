package rebelalliance.smartcash;

import javafx.application.Application;
import javafx.stage.Stage;

public class SmartCash extends Application {
    SceneManager sceneManager;

    @Override
    public void start(Stage stage) {
        this.sceneManager = new SceneManager(stage);

        // Window setup.
        stage.setTitle("SmartCash");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);

        // Show.
        // TODO: Change default scene.
        sceneManager.setScene("register");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
