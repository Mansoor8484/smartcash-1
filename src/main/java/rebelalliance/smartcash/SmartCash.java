package rebelalliance.smartcash;

import javafx.application.Application;
import javafx.stage.Stage;
import rebelalliance.smartcash.database.DatabaseManager;
import rebelalliance.smartcash.http.SmartCashHttpServer;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.scene.SceneManager;

public class SmartCash extends Application {
    DatabaseManager databaseManager;

    @Override
    public void start(Stage stage) {
        this.databaseManager = new DatabaseManager();

        // TODO: Remove this.
        // this.databaseManager.registerUser("test@test.com", "test");
        this.databaseManager.registerUser("test@test.com", "test", "JQPVUOX3WHBBOZILECL7SG4RKP5ZULS7");

        // Window setup.
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);

        // Show.
        SceneManager sceneManager = new SceneManager(stage, databaseManager);
        sceneManager.setScene(SCScene.LOGIN);
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));

        // HTTP server.
        SmartCashHttpServer httpServer = new SmartCashHttpServer(sceneManager.getLedger());
        httpServer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
