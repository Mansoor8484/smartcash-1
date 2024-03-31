module rebelalliance.smartcash {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;
    requires java.sql;
    requires totp;

    opens rebelalliance.smartcash to javafx.graphics;

    opens rebelalliance.smartcash.component to javafx.fxml;
    opens rebelalliance.smartcash.controller to javafx.fxml;
    opens rebelalliance.smartcash.controller.modal to javafx.fxml;
    opens rebelalliance.smartcash.scene to javafx.graphics;
    opens rebelalliance.smartcash.controller.scene to javafx.fxml;
    opens rebelalliance.smartcash.controller.scene.transactions to javafx.fxml;
}
