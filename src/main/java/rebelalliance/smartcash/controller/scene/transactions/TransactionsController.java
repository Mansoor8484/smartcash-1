package rebelalliance.smartcash.controller.scene.transactions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.controller.modal.TransactionModalController;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.scene.SCScene;

public class TransactionsController extends BaseController implements IController {
    @FXML
    private TableView<TransactionTableItem> table;
    @FXML
    private TableColumn<TransactionTableItem, String> dateColumn;
    @FXML
    private TableColumn<TransactionTableItem, String> amountColumn;
    @FXML
    private TableColumn<TransactionTableItem, String> accountColumn;
    @FXML
    private TableColumn<TransactionTableItem, String> categoryColumn;
    @FXML
    private TableColumn<TransactionTableItem, String> notesColumn;


    @Override
    public void init() {
        this.dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getDate()
        ));
        this.amountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAmount()
        ));
        this.accountColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAccount()
        ));
        this.categoryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getCategory()
        ));
        this.notesColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getNotes()
        ));

        this.update();
    }

    @Override
    public void update() {
        this.table.getItems().clear();

        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getLedger()) {
            if(ledgerItem instanceof Transaction transaction) {
                this.table.getItems().add(new TransactionTableItem(transaction));
            }
            if(ledgerItem instanceof Adjustment adjustment) {
                this.table.getItems().add(new TransactionTableItem(adjustment));
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
