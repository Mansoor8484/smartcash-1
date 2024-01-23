package rebelalliance.smartcash.controller.scene;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

public class TransactionsController extends BaseController implements IController {
    @FXML
    private TableView<LedgerItem> table;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> amountColumn;
    @FXML
    private TableColumn<Transaction, String> accountColumn;
    @FXML
    private TableColumn<Transaction, String> categoryColumn;
    @FXML
    private TableColumn<Transaction, String> notesColumn;


    @Override
    public void init() {
        this.dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(DateUtil.format(cellData.getValue().getDate())));
        this.amountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(NumberUtil.formatAsAmount(cellData.getValue().getAmount())));
        this.accountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAccountFrom().toString()));
        this.categoryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory().toString()));
        this.notesColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));

        this.update();
    }

    @Override
    public void update() {
        this.table.getItems().clear();

        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getLedger()) {
            if(ledgerItem instanceof Transaction transaction) {
                this.table.getItems().add(transaction);
            }
        }
    }

    @FXML
    public void addTransaction() {
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
