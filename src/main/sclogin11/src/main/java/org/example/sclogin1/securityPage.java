package org.example.sclogin1;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class securityPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showSecurityPage(primaryStage);
    }

    public void showSecurityPage(Stage primaryStage) {
        primaryStage.setTitle("Security");

        StackPane securityRoot = new StackPane();
        securityRoot.setBackground(new Background(new BackgroundFill(Color.web("3b3b3b"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label securityLabel = new Label("Security");
        securityLabel.setUnderline(true);
        securityLabel.setFont(Font.font("Cambria", FontWeight.BOLD, 28));
        securityLabel.setTextFill(Paint.valueOf("#DFF0D8"));
        securityRoot.getChildren().add(securityLabel);
        StackPane.setAlignment(securityLabel, Pos.TOP_LEFT);
        securityLabel.setPadding(new Insets(30,0,0,40));



        Scene securityScene = new Scene(securityRoot, 1650, 1080);
        primaryStage.setScene(securityScene);
        primaryStage.show();




    }


}
