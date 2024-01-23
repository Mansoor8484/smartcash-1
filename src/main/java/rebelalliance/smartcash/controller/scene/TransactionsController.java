package rebelalliance.smartcash.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.controller.modal.TransactionModalController;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.scene.SCScene;

public class TransactionsController extends BaseController implements IController {
    @FXML
    private VBox vBox;

    @Override
    public void init() {
        this.update();
    }

    @Override
    public void update() {
        vBox.getChildren().clear();

        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getLedger()) {
            Text text = new Text();
            text.setText(ledgerItem.toString());
            vBox.getChildren().add(text);
        }
    }

    public void addTestTransaction() {
        try {
            FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/modal/transaction.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("New Transaction");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);

            TransactionModalController controller = loader.getController();
            controller.setStage(stage);
            controller.setAccountOptions(this.sceneManager.getLedger().getAccounts());
            controller.setCategoryOptions(this.sceneManager.getLedger().getCategories());

            stage.showAndWait();
            if(controller.shouldSave()) {
                Transaction transaction = new Transaction(
                        controller.getAmount(),
                        controller.getAccountFrom(),
                        controller.getCategory()
                );
                this.sceneManager.getLedger().add(transaction);
            }

            this.update();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void testGoToOverview(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.OVERVIEW);
    }
}
