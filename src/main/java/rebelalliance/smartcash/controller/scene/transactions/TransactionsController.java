package rebelalliance.smartcash.controller.scene.transactions;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import rebelalliance.smartcash.Modal;
import rebelalliance.smartcash.component.Navbar;
import rebelalliance.smartcash.component.menuitem.ArchiveMenuItem;
import rebelalliance.smartcash.component.menuitem.DeleteMenuItem;
import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.controller.modal.AccountCreationModalController;
import rebelalliance.smartcash.controller.modal.AdjustmentModalController;
import rebelalliance.smartcash.controller.modal.CategoryCreationModalController;
import rebelalliance.smartcash.controller.modal.TransactionModalController;
import rebelalliance.smartcash.ledger.item.Adjustment;
import rebelalliance.smartcash.ledger.container.Category;
import rebelalliance.smartcash.ledger.item.LedgerItem;
import rebelalliance.smartcash.ledger.item.Transfer;
import rebelalliance.smartcash.ledger.item.transaction.Transaction;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.util.DateUtil;
import rebelalliance.smartcash.util.NumberUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TransactionsController extends BaseController implements IController {
    @FXML
    private TableView<TransactionTableItem> transactionsTable;
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
        this.addHeader();

        // Table.
        this.transactionsTable.setRowFactory(table -> {
            final TableRow<TransactionTableItem> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().add(new MenuItem("Edit"));
            final MenuItem deleteMenuItem = new MenuItem("Delete");
            deleteMenuItem.setOnAction(event -> {
                TransactionTableItem item = row.getItem();
                this.sceneManager.getLedger().remove(item.getLedgerItem());
                this.updateTable();
            });
            contextMenu.getItems().add(deleteMenuItem);
            row.setContextMenu(contextMenu);
            return row;
        });

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
        this.updateTable();
        this.updateAccounts();
        this.updateCategories();
    }

    public void updateTable() {
        this.transactionsTable.getItems().clear();

        // Table.
        for(LedgerItem ledgerItem : this.sceneManager.getLedger().getItems()) {
            if(ledgerItem.getAccountFrom().isArchived()) {
                continue;
            }

            if(ledgerItem instanceof Transaction transaction) {
                if(transaction.getCategory().isArchived()) {
                    continue;
                }
                if(!this.accountDisplay.get(transaction.getAccountFrom())) {
                    continue;
                }
                if(!this.categoryDisplay.get(transaction.getCategory())) {
                    continue;
                }
                TransactionTableItem item = new TransactionTableItem(transaction);
                item.setLedgerItem(transaction);
                this.transactionsTable.getItems().add(item);
            }
            if(ledgerItem instanceof Adjustment adjustment) {
                if(!this.showAdjustments.isSelected()) {
                    continue;
                }
                if(!this.accountDisplay.get(adjustment.getAccountFrom())) {
                    continue;
                }
                TransactionTableItem item = new TransactionTableItem(adjustment);
                item.setLedgerItem(adjustment);
                this.transactionsTable.getItems().add(item);
            }
            if(ledgerItem instanceof Transfer transfer) {
                if(!this.showTransfers.isSelected() || transfer.getAccountTo().isArchived()) {
                    continue;
                }
                if(this.accountDisplay.get(transfer.getAccountFrom())) {
                    TransactionTableItem item = new TransactionTableItem(
                            DateUtil.format(transfer.getDate()),
                            NumberUtil.formatAsAmount(-transfer.getAmount()),
                            transfer.getAccountFrom().toString(),
                            "Transfer",
                            transfer.getNotes()
                    );
                    item.setLedgerItem(transfer);
                    this.transactionsTable.getItems().add(item);
                }
                if(this.accountDisplay.get(transfer.getAccountTo())) {
                    TransactionTableItem item = new TransactionTableItem(
                            DateUtil.format(transfer.getDate()),
                            NumberUtil.formatAsAmount(transfer.getAmount()),
                            transfer.getAccountTo().toString(),
                            "Transfer",
                            transfer.getNotes()
                    );
                    item.setLedgerItem(transfer);
                    this.transactionsTable.getItems().add(item);
                }
            }
        }
    }

    public void updateAccounts() {
        this.accountList.getChildren().clear();
        List<Account> accounts = this.sceneManager.getLedger().getAccounts();
        accounts.sort(Comparator.comparing(Account::toString));
        for(Account account : accounts) {
            if(account.isArchived()) {
                continue;
            }
            CheckBox checkBox = new CheckBox();
            checkBox.setText(account.getName());
            checkBox.setSelected(this.accountDisplay.get(account));
            checkBox.setOnAction(event -> {
                this.accountDisplay.put(account, checkBox.isSelected());
                this.update();
            });
            checkBox.setCursor(Cursor.HAND);

            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().add(new MenuItem("Edit"));
            contextMenu.getItems().add(new ArchiveMenuItem(account, () -> {
                this.updateAccounts();
                this.updateTable();
            }));
            contextMenu.getItems().add(new DeleteMenuItem(account, () -> {
                this.updateAccounts();
                this.updateTable();
            }));
            checkBox.setContextMenu(contextMenu);

            this.accountList.getChildren().add(checkBox);
        }
    }

    public void updateCategories() {
        this.categoryList.getChildren().clear();
        List<Category> categories = this.sceneManager.getLedger().getCategories();
        categories.sort(Comparator.comparing(Category::toString));
        for(Category category : categories) {
            if(category.isArchived()) {
                continue;
            }
            CheckBox checkBox = new CheckBox();
            checkBox.setText(category.getName());
            checkBox.setSelected(this.categoryDisplay.get(category));
            checkBox.setOnAction(event -> {
                this.categoryDisplay.put(category, checkBox.isSelected());
                this.update();
            });
            checkBox.setCursor(Cursor.HAND);

            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().add(new MenuItem("Edit"));
            contextMenu.getItems().add(new ArchiveMenuItem(category, () -> {
                this.updateCategories();
                this.updateTable();
            }));
            contextMenu.getItems().add(new DeleteMenuItem(category, () -> {
                this.updateCategories();
                this.updateTable();
            }));
            checkBox.setContextMenu(contextMenu);

            this.categoryList.getChildren().add(checkBox);
        }
    }

    @FXML
    public void addTransaction() {
        Modal transactionModal = new Modal("New Transaction", "create-transaction");
        TransactionModalController transactionModalController = (TransactionModalController) transactionModal.getController();
        transactionModalController.setStage(transactionModal.getStage());
        transactionModalController.setAccountOptions(this.sceneManager.getLedger().getAccounts());
        transactionModalController.setCategoryOptions(this.sceneManager.getLedger().getCategories());
        transactionModalController.init();

        transactionModal.showAndWait();
        if(transactionModalController.shouldSave()) {
            Transaction transaction = new Transaction(
                    transactionModalController.getAmount(),
                    transactionModalController.getAccountFrom(),
                    transactionModalController.getCategory(),
                    transactionModalController.getDate()
            );
            transaction.setNotes(transactionModalController.getNotes());
            this.sceneManager.getLedger().add(transaction);
        }
        this.updateTable();
    }

    @FXML
    public void addAdjustment() {
        Modal adjustmentModal = new Modal("New Adjustment", "create-adjustment");
        AdjustmentModalController adjustmentModalController = (AdjustmentModalController) adjustmentModal.getController();
        adjustmentModalController.setStage(adjustmentModal.getStage());
        adjustmentModalController.setAccountOptions(this.sceneManager.getLedger().getAccounts());
        adjustmentModalController.init();

        adjustmentModal.showAndWait();
        if(adjustmentModalController.shouldSave()) {
            Adjustment adjustment = new Adjustment(
                    adjustmentModalController.getAccountFrom(),
                    adjustmentModalController.getAmount(),
                    adjustmentModalController.getDate()
            );
            adjustment.setNotes(adjustmentModalController.getNotes());
            this.sceneManager.getLedger().add(adjustment);
        }
        this.update();
    }

    @FXML
    public void addAccount() {
        Account existingAccount;
        AccountCreationModalController accountModalController;

        String newAccountName;
        do {
            Modal accountModal = new Modal("New Account", "create-account");
            accountModalController = (AccountCreationModalController) accountModal.getController();
            accountModalController.setStage(accountModal.getStage());

            accountModal.showAndWait();
            newAccountName = accountModalController.getAccountName().trim();
            existingAccount = this.sceneManager.getLedger().getAccount(newAccountName);
        }while(existingAccount != null);

        if(accountModalController.shouldSave()) {
            Account account = new Account(newAccountName);
            this.sceneManager.getLedger().addAccount(account);
            this.accountDisplay.put(account, true);
        }
        this.updateAccounts();
    }

    @FXML
    public void addCategory() {
        Category existingCategory;
        CategoryCreationModalController accountModalController;

        String newCategoryName;
        do {
            Modal accountModal = new Modal("New Category", "create-category");
            accountModalController = (CategoryCreationModalController) accountModal.getController();
            accountModalController.setStage(accountModal.getStage());

            accountModal.showAndWait();
            newCategoryName = accountModalController.getCategoryName().trim();
            existingCategory = this.sceneManager.getLedger().getCategory(newCategoryName);
        }while(existingCategory != null
                || accountModalController.getCategoryName().equals("Transfer")
                || accountModalController.getCategoryName().equals("Adjustment")
        );

        if(accountModalController.shouldSave()) {
            Category category = new Category(newCategoryName);
            this.sceneManager.getLedger().addCategory(category);
            this.categoryDisplay.put(category, true);
        }
        this.updateCategories();
    }
}
