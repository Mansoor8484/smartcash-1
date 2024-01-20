module rebelalliance.smartcash {
    requires javafx.controls;
    requires javafx.fxml;

    opens rebelalliance.smartcash to javafx.graphics;
    opens rebelalliance.smartcash.scene to javafx.fxml;
}
