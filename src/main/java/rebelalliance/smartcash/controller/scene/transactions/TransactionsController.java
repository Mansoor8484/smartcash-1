package rebelalliance.smartcash.controller.scene.transactions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.controller.modal.TransactionModalController;
import rebelalliance.smartcash.ledger.Adjustment;
import rebelalliance.smartcash.ledger.Category;
import rebelalliance.smartcash.ledger.LedgerItem;
import rebelalliance.smartcash.ledger.Transfer;
import rebelalliance.smartcash.ledger.transaction.Transaction;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

import java.util.HashMap;

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
    @FXML
    private VBox accountList;
    @FXML
    private VBox categoryList;
    @FXML
    private CheckBox showAdjustments;
    @FXML
    private CheckBox showTransfers;

    HashMap<Account, Boolean> accountDisplay = new HashMap<>();
    HashMap<Category, Boolean> categoryDisplay = new HashMap<>();

    @Override
    public void init() {
        // Table.
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

        // Accounts.
        for(Account account : this.sceneManager.getLedger().getAccounts()) {
            this.accountDisplay.put(account, true);
        }

        // Categories.
        for(Category category : this.sceneManager.getLedger().getCategories()) {
            this.categoryDisplay.put(category, true);
        }
        this.showAdjustments.setOnAction(event -> this.update());
        this.showTransfers.setOnAction(event -> this.update());

        this.update();
    }


    @Override
    public void update() {
        this.table.getItems().clear();

        // Table.
        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getLedger()) {
            if(ledgerItem instanceof Transaction transaction) {
                if(!this.accountDisplay.get(transaction.getAccountFrom())) {
                    continue;
                }
                if(!this.categoryDisplay.get(transaction.getCategory())) {
                    continue;
                }
                this.table.getItems().add(new TransactionTableItem(transaction));
            }
            if(ledgerItem instanceof Adjustment adjustment) {
                if(!this.showAdjustments.isSelected()) {
                    continue;
                }
                if(!this.accountDisplay.get(adjustment.getAccountFrom())) {
                    continue;
                }
                this.table.getItems().add(new TransactionTableItem(adjustment));
            }
            if(ledgerItem instanceof Transfer transfer) {
                if(!this.showTransfers.isSelected()) {
                    continue;
                }
                if(this.accountDisplay.get(transfer.getAccountFrom())) {
                    this.table.getItems().add(new TransactionTableItem(
                            DateUtil.format(transfer.getDate()),
                            NumberUtil.formatAsAmount(-transfer.getAmount()),
                            transfer.getAccountFrom().toString(),
                            "Transfer",
                            transfer.getDescription()
                    ));
                }
                if(this.accountDisplay.get(transfer.getAccountTo())) {
                    this.table.getItems().add(new TransactionTableItem(
                            DateUtil.format(transfer.getDate()),
                            NumberUtil.formatAsAmount(transfer.getAmount()),
                            transfer.getAccountTo().toString(),
                            "Transfer",
                            transfer.getDescription()
                    ));
                }
            }
        }

        // Account checkboxes.
        this.accountList.getChildren().clear();
        for(Account account : this.sceneManager.getLedger().getAccounts()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(account.getName());
            checkBox.setSelected(this.accountDisplay.get(account));
            checkBox.setOnAction(event -> {
                this.accountDisplay.put(account, checkBox.isSelected());
                this.update();
            });
            this.accountList.getChildren().add(checkBox);
        }

        // Category checkboxes.
        this.categoryList.getChildren().clear();
        for(Category category : this.sceneManager.getLedger().getCategories()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(category.getName());
            checkBox.setSelected(this.categoryDisplay.get(category));
            checkBox.setOnAction(event -> {
                this.categoryDisplay.put(category, checkBox.isSelected());
                this.update();
            });
            this.categoryList.getChildren().add(checkBox);
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
