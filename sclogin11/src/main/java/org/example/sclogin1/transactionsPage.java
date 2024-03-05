package org.example.sclogin1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class transactionsPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showTransactionsPage(primaryStage);
    }


    public void showTransactionsPage(Stage primaryStage) {
        primaryStage.setTitle("Transactions");

        StackPane transactionsRoot = new StackPane();
        transactionsRoot.setBackground(new Background(new BackgroundFill(Color.web("3b3b3b"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label transactionsLabel = new Label("Transactions");
        transactionsLabel.setUnderline(true);
        transactionsLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 28));
        transactionsLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        transactionsRoot.getChildren().add(transactionsLabel);
        StackPane.setAlignment(transactionsLabel, Pos.TOP_LEFT);
        transactionsLabel.setPadding(new Insets(30,0,0,40));



        Scene securityScene = new Scene(transactionsRoot, 1650, 1080);
        primaryStage.setScene(securityScene);
        primaryStage.show();










    }
    



}
