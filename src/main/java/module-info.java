module rebelalliance.smartcash {
    requires javafx.controls;
    requires javafx.fxml;

    opens rebelalliance.smartcash to javafx.graphics;

    opens rebelalliance.smartcash.component to javafx.fxml;
    opens rebelalliance.smartcash.controller to javafx.fxml;
    opens rebelalliance.smartcash.scene to javafx.graphics;
}
