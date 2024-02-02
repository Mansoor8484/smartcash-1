package rebelalliance.smartcash.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
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
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

public class OverviewController extends BaseController implements IController {
    @FXML
    private VBox accountsBox;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<Number, Number> accountsWeekOverWeek;
    @FXML
    private NumberAxis lineChartXAxis;
    @FXML
    private PieChart spendPieChart;

    private LedgerStats ledgerStats;

    @Override
    public void init() {
        this.ledgerStats = new LedgerStats(this.sceneManager.getLedger());

        this.lineChartXAxis.setForceZeroInRange(false);
        this.lineChartXAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.lineChartXAxis) {
            @Override
            public String toString(Number object) {
                return DateUtil.format(LocalDate.ofEpochDay(object.longValue() / (60 * 60 * 24)));
            }
        });
        this.lineChartXAxis.setMinorTickVisible(false);
        this.lineChartXAxis.setTickUnit(60 * 60 * 24);
        this.lineChartXAxis.setAutoRanging(false);

        this.update();
    }

    @Override
    public void update() {
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

        this.pieChart.getData().clear();
        for(Account account : accounts) {
            if(account.isArchived()) {
                continue;
            }
            double balance = account.getBalance();
            if(balance == 0) {
                continue;
            }

            this.pieChart.getData().add(new PieChart.Data(account.toString(), balance));
        }
        this.pieChart.setLabelsVisible(true);

        // Line graph.
        HashMap<Account, XYChart.Series<Number, Number>> data = new HashMap<>();
        for(Account account : accounts) {
            if(account.isArchived()) {
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
                if(account.isArchived()) {
                    continue;
                }
                XYChart.Series<Number, Number> series = data.get(account);
                series.getData().add(new XYChart.Data<>(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(), dayBalances.get(account)));
            }
        }
        this.accountsWeekOverWeek.getData().setAll(data.values());

        this.lineChartXAxis.setLowerBound(this.sceneManager.getLedger().getLedger().get(0).getDate().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
        this.lineChartXAxis.setUpperBound(this.sceneManager.getLedger().getLedger().get(this.sceneManager.getLedger().getLedger().size() - 1).getDate().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());

        // Category Spend
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
