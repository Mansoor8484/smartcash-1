package rebelalliance.smartcash.controller.scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.component.BigNumber;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.scene.SCScene;
import rebelalliance.smartcash.statistic.LedgerStats;
import rebelalliance.smartcash.util.ArrayUtil;
import rebelalliance.smartcash.util.DateUtil;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

public class OverviewController extends BaseController implements IController {
    @FXML
    private VBox accountsBox;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<String, Double> accountsWeekOverWeek;

    private LedgerStats ledgerStats;

    @Override
    public void init() {
        this.ledgerStats = new LedgerStats(this.sceneManager.getLedger());

        this.update();
    }

    @Override
    public void update() {
        this.accountsBox.getChildren().clear();

        List<Account> accounts = this.sceneManager.getLedger().getAccounts();
        for(Account account : accounts) {
            BigNumber bigNumber = new BigNumber();
            bigNumber.setAccountName(account.toString());
            bigNumber.setAmount(account.getBalance());
            this.accountsBox.getChildren().add(bigNumber);
        }

        this.pieChart.getData().clear();
        for(Account account : accounts) {
            double balance = account.getBalance();
            if(balance == 0) {
                continue;
            }

            this.pieChart.getData().add(new PieChart.Data(account.toString(), balance));
        }
        this.pieChart.setLabelsVisible(true);

        // Line graph.
        HashMap<Account, XYChart.Series<String, Double>> data = new HashMap<>();
        for(Account account : accounts) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(account.toString());

            data.put(account, series);
        }

        SortedMap<LocalDate, HashMap<Account, Double>> dayOverDay = this.ledgerStats.getBalanceDayOverDay();

        for(LocalDate date : dayOverDay.keySet()) {
            HashMap<Account, Double> dayBalances = dayOverDay.get(date);
            for(Account account : dayBalances.keySet()) {
                XYChart.Series<String, Double> series = data.get(account);
                series.getData().add(new XYChart.Data<>(DateUtil.format(date), dayBalances.get(account)));
            }
        }

        this.accountsWeekOverWeek.getData().clear();
        this.accountsWeekOverWeek.getData().setAll(data.values());
    }

    public void testGoToTransactions(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }
}
