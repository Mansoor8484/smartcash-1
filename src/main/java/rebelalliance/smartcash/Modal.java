package rebelalliance.smartcash;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rebelalliance.smartcash.controller.BaseController;

public class Modal {
    private FXMLLoader loader;
    private Stage stage;

    public Modal(String title, String path) {
        try {
            this.loader = new FXMLLoader(SmartCash.class.getResource("fxml/modal/" + path + ".fxml"));
            Parent parent = this.loader.load();

            this.stage = new Stage();
            this.stage.initStyle(StageStyle.UTILITY);
            this.stage.setTitle(title);
            this.stage.setScene(new Scene(parent));
            this.stage.setResizable(false);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showAndWait() {
        this.stage.showAndWait();
    }

    public BaseController getController() {
        return this.loader.getController();
    }

    public Stage getStage() {
        return this.stage;
    }
}
