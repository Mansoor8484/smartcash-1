package rebelalliance.smartcash.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import rebelalliance.smartcash.SmartCash;

import java.io.IOException;

public class RegisterScene {
    Scene scene;

    public RegisterScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SmartCash.class.getResource("register.fxml"));
        this.scene = new Scene(fxmlLoader.load());
    }

    public Scene getScene() {
        return this.scene;
    }
}
