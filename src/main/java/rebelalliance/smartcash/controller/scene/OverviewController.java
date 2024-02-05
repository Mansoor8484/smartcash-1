package rebelalliance.smartcash.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import rebelalliance.smartcash.file.UserPreferences;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.component.BigNumber;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.ledger.container.Category;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.ledger.statistic.LedgerStats;
import rebelalliance.smartcash.util.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class OverviewController extends BaseController implements IController {
    @FXML
    private VBox accountsBox;
    @FXML
    private PieChart compositionPieChart;
    @FXML
    private LineChart<Number, Number> historicalLineChart;
    @FXML
    private NumberAxis historicalLineChartXAxis;
    @FXML
    private PieChart spendPieChart;

    private LedgerStats ledgerStats;

    private HashMap<Account, Boolean> accountCompositionDisplay;
    private HashMap<Account, Boolean> historicalAccountDisplay;

    @Override
    public void init() {
        this.ledgerStats = new LedgerStats(this.sceneManager.getLedger());
        this.accountCompositionDisplay = new HashMap<>();
        this.historicalAccountDisplay = new HashMap<>();

        this.historicalLineChartXAxis.setForceZeroInRange(false);
        this.historicalLineChartXAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.historicalLineChartXAxis) {
            @Override
            public String toString(Number object) {
                return DateUtil.format(LocalDate.ofEpochDay(object.longValue() / (60 * 60 * 24)));
            }
        });
        this.historicalLineChartXAxis.setMinorTickVisible(false);
        this.historicalLineChartXAxis.setTickUnit(60 * 60 * 24);
        this.historicalLineChartXAxis.setAutoRanging(false);

        // Load user preferences.
        UserPreferences userPreferences = this.sceneManager.getUserPreferences();
        if(!userPreferences.containsKey("hiddenAccountCompositionAccounts")) {
            userPreferences.setString("hiddenAccountCompositionAccounts", "");
        }
        String[] hiddenAccountCompositionAccounts = userPreferences.getString("hiddenAccountCompositionAccounts").split(",");
        for(String accountName : hiddenAccountCompositionAccounts) {
            Account account = this.sceneManager.getLedger().getAccount(accountName);
            if(account != null) {
                this.accountCompositionDisplay.put(account, false);
            }
        }

        this.update();
    }

    @Override
    public void update() {
        this.updateAccountDisplay();
        this.updateCompositionPieChart();
        this.updateHistoricalLineChart();
        this.updateSpendPieChart();
    }

    public void updateAccountDisplay() {
        this.accountsBox.getChildren().clear();
        List<Account> accounts = this.sceneManager.getLedger().getAccounts();
        for(Account account : accounts) {
            if(account.isArchived()) {
                continue;
            }
            BigNumber bigNumber = new BigNumber();
            bigNumber.setAccountName(account.toString());
            bigNumber.setAmount(account.getBalance());
            this.accountsBox.getChildren().add(bigNumber);
        }
    }

    public void updateCompositionPieChart() {
        this.compositionPieChart.getData().clear();
        ContextMenu contextMenu = new ContextMenu();
        for(Account account : this.sceneManager.getLedger().getAccounts()) {
            if(!this.accountCompositionDisplay.containsKey(account)) {
                this.accountCompositionDisplay.put(account, true);
            }

            MenuItem menuItem = new MenuItem((this.accountCompositionDisplay.get(account) ? "Hide " : "Show ") + account);
            menuItem.setOnAction(event -> {
                this.accountCompositionDisplay.put(account, !this.accountCompositionDisplay.get(account));
                UserPreferences userPreferences = this.sceneManager.getUserPreferences();
                String[] savedHiddenAccounts = this.accountCompositionDisplay.keySet().stream()
                        .filter(accountDisplay -> !this.accountCompositionDisplay.get(accountDisplay)).map(Objects::toString).toArray(String[]::new);
                userPreferences.setString("hiddenAccountCompositionAccounts", String.join(",", savedHiddenAccounts));
                this.updateCompositionPieChart();
            });
            contextMenu.getItems().add(menuItem);

            if(account.isArchived() || !this.accountCompositionDisplay.get(account)) {
                continue;
            }
            double balance = account.getBalance();
            if(balance == 0) {
                continue;
            }

            this.compositionPieChart.getData().add(new PieChart.Data(account.toString(), balance));
        }
        this.compositionPieChart.setOnContextMenuRequested(event -> contextMenu.show(this.compositionPieChart, event.getScreenX(), event.getScreenY()));
        this.compositionPieChart.setCursor(Cursor.HAND);
    }

    public void updateHistoricalLineChart() {
        this.historicalLineChart.getData().clear();

        Ledger ledger = this.sceneManager.getLedger();
        if(ledger.getItems().size() == 0) {
            return;
        }

        ContextMenu contextMenu = new ContextMenu();
        HashMap<Account, XYChart.Series<Number, Number>> data = new HashMap<>();
        for(Account account : ledger.getAccounts()) {
            if(!this.historicalAccountDisplay.containsKey(account)) {
                this.historicalAccountDisplay.put(account, true);
            }

            MenuItem menuItem = new MenuItem((this.historicalAccountDisplay.get(account) ? "Hide " : "Show ") + account);
            menuItem.setOnAction(event -> {
                this.historicalAccountDisplay.put(account, !this.historicalAccountDisplay.get(account));
                this.updateHistoricalLineChart();
            });
            contextMenu.getItems().add(menuItem);

            if(account.isArchived() || !this.historicalAccountDisplay.get(account)) {
                continue;
            }
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(account.toString());
            data.put(account, series);
        }

        SortedMap<LocalDate, HashMap<Account, Double>> dayOverDay = this.ledgerStats.getBalanceDayOverDay();
        for(LocalDate date : dayOverDay.keySet()) {
            HashMap<Account, Double> dayBalances = dayOverDay.get(date);
            for(Account account : dayBalances.keySet()) {
                if(account.isArchived() || !this.historicalAccountDisplay.get(account)) {
                    continue;
                }
                XYChart.Series<Number, Number> series = data.get(account);
                series.getData().add(new XYChart.Data<>(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), dayBalances.get(account)));
            }
        }
        this.historicalLineChart.getData().setAll(data.values());

        this.historicalLineChartXAxis.setLowerBound(ledger.getItems().get(0).getDate().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
        this.historicalLineChartXAxis.setUpperBound(ledger.getItems().get(ledger.getItems().size() - 1).getDate().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());

        this.historicalLineChart.setOnContextMenuRequested(event -> contextMenu.show(this.historicalLineChart, event.getScreenX(), event.getScreenY()));
        this.historicalLineChart.setCursor(Cursor.HAND);
    }

    public void updateSpendPieChart() {
        this.spendPieChart.getData().clear();

        HashMap<Category, Double> categorySpend = this.ledgerStats.getCategorySpend(LocalDate.now().minusDays(7), LocalDate.now());
        for(Category category : categorySpend.keySet()) {
            if(category.isArchived()) {
                continue;
            }
            double amount = categorySpend.get(category);
            if(amount == 0) {
                continue;
            }
            this.spendPieChart.getData().add(new PieChart.Data(category.toString(), Math.abs(amount)));
        }
    }

    public void testGoToTransactions(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }
}
