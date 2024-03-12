package rebelalliance.smartcash.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.scene.SceneManager;

public class Navbar extends HBox {
    SceneManager sceneManager;

    public Navbar(SceneManager sceneManager) {
        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/component/navbar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }

        this.sceneManager = sceneManager;
    }

    @FXML
    protected void onOverviewClick() {
        this.sceneManager.setScene(SCScene.OVERVIEW);
    }

    @FXML
    protected void onTransactionsClick() {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }

    @FXML
    protected void onBudgetingClick() {
        this.sceneManager.setScene(SCScene.BUDGETING);
    }

    @FXML
    protected void onSecurityClick() {
        this.sceneManager.setScene(SCScene.SECURITY);
    }
}
