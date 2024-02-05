package rebelalliance.smartcash.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import rebelalliance.smartcash.file.preferences.UserPreference;
import rebelalliance.smartcash.ledger.Ledger;
import rebelalliance.smartcash.ledger.container.Account;
import rebelalliance.smartcash.component.BigNumber;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.ledger.container.Category;
import rebelalliance.smartcash.ledger.container.Container;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.ledger.statistic.LedgerStats;
import rebelalliance.smartcash.util.DateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    private HashMap<Container, Boolean> accountCompositionDisplay;
    private HashMap<Container, Boolean> historicalAccountDisplay;
    private HashMap<Container, Boolean> categorySpendDisplay;

    @Override
    public void init() {
        this.ledgerStats = new LedgerStats(this.sceneManager.getLedger());
        this.userPreferences = this.sceneManager.getUserPreferences();

        this.accountCompositionDisplay = new HashMap<>();
        this.historicalAccountDisplay = new HashMap<>();
        this.categorySpendDisplay = new HashMap<>();

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
        this.loadUserPreferences();

        this.update();
    }

    @Override
    public void update() {
        this.updateAccountDisplay();
        this.updateCompositionPieChart();
        this.updateHistoricalLineChart();
        this.updateSpendPieChart();
    }

    public String getHiddenContainers(HashMap<Container, Boolean> hashMap) {
        Set<Container> keys = hashMap.keySet();
        String[] hiddenContainers = keys.stream().filter(account -> !hashMap.get(account)).map(Objects::toString).toArray(String[]::new);

        return String.join(",", hiddenContainers);
    }

    public void loadDisplayMapUserPreferences(UserPreference preference, HashMap<Container, Boolean> displayMap) {
        this.userPreferences.ensureKey(preference);

        String[] values = this.userPreferences.getString(preference).split(",");
        for(String container : values) {
            Container account = this.sceneManager.getLedger().getContainer(container);
            if(account != null) {
                displayMap.put(account, false);
            }
        }
    }

    public void loadUserPreferences() {
        this.loadDisplayMapUserPreferences(UserPreference.OVERVIEW_HIDDEN_COMPOSITION_ACCOUNTS, this.accountCompositionDisplay);
        this.loadDisplayMapUserPreferences(UserPreference.OVERVIEW_HIDDEN_HISTORICAL_ACCOUNTS, this.historicalAccountDisplay);
        this.loadDisplayMapUserPreferences(UserPreference.OVERVIEW_HIDDEN_CATEGORY_SPEND_CATEGORIES, this.categorySpendDisplay);
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
                this.updateCompositionPieChart();
                this.userPreferences.setString(
                        UserPreference.OVERVIEW_HIDDEN_COMPOSITION_ACCOUNTS,
                        this.getHiddenContainers(this.accountCompositionDisplay)
                );
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
                this.userPreferences.setString(
                        UserPreference.OVERVIEW_HIDDEN_HISTORICAL_ACCOUNTS,
                        this.getHiddenContainers(this.historicalAccountDisplay)
                );
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

        ContextMenu contextMenu = new ContextMenu();
        HashMap<Category, Double> categorySpend = this.ledgerStats.getCategorySpend(LocalDate.now().minusDays(7), LocalDate.now());
        for(Category category : categorySpend.keySet()) {
            if(!this.categorySpendDisplay.containsKey(category)) {
                this.categorySpendDisplay.put(category, true);
            }

            MenuItem menuItem = new MenuItem((this.categorySpendDisplay.get(category) ? "Hide " : "Show ") + category);
            menuItem.setOnAction(event -> {
                this.categorySpendDisplay.put(category, !this.categorySpendDisplay.get(category));
                this.updateSpendPieChart();
                this.userPreferences.setString(
                        UserPreference.OVERVIEW_HIDDEN_CATEGORY_SPEND_CATEGORIES,
                        this.getHiddenContainers(this.categorySpendDisplay)
                );
            });
            contextMenu.getItems().add(menuItem);

            if(category.isArchived() || !this.categorySpendDisplay.get(category)) {
                continue;
            }
            double amount = categorySpend.get(category);
            if(amount == 0) {
                continue;
            }

            this.spendPieChart.getData().add(new PieChart.Data(category.toString(), Math.abs(amount)));
        }
        this.spendPieChart.setOnContextMenuRequested(event -> contextMenu.show(this.spendPieChart, event.getScreenX(), event.getScreenY()));
        this.spendPieChart.setCursor(Cursor.HAND);
    }

    public void testGoToTransactions(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }
}
