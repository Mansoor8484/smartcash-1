package rebelalliance.smartcash.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import rebelalliance.smartcash.SmartCash;
import rebelalliance.smartcash.util.MathUtil;
import rebelalliance.smartcash.util.NumberUtil;

public class BigNumber extends VBox {
    @FXML
    private Text accountName;
    @FXML
    private Text amount;

    public BigNumber() {
        FXMLLoader loader = new FXMLLoader(SmartCash.class.getResource("fxml/component/big-number.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAccountName(String accountName) {
        this.accountName.setText(accountName);
    }

    public void setAmount(double amount) {
        double rounded = MathUtil.round(amount, 2);

        this.amount.setText(NumberUtil.formatAsAmount(rounded));
    }
}
