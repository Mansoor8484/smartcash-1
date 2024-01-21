package rebelalliance.smartcash;

import javafx.application.Application;
import javafx.stage.Stage;
import rebelalliance.smartcash.scene.register.RegisterScene;

import java.io.IOException;

public class SmartCash extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Window setup.
        stage.setTitle("SmartCash");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);

        // Show.
        stage.setScene(new RegisterScene().getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
