package rebelalliance.smartcash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.account.Account;
import rebelalliance.smartcash.component.BigNumber;
import rebelalliance.smartcash.scene.SCScene;

import java.util.List;

public class OverviewController extends BaseController implements IController {
    @FXML
    private VBox accountsBox;
    @FXML
    private PieChart pieChart;

    @Override
    public void init() {
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
    }

    public void testGoToTransactions(ActionEvent actionEvent) {
        this.sceneManager.setScene(SCScene.TRANSACTIONS);
    }
}
