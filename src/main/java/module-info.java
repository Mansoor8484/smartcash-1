module rebelalliance.smartcash {
    requires javafx.controls;
    requires javafx.fxml;

    opens rebelalliance.smartcash to javafx.graphics;

    // Scenes.
    opens rebelalliance.smartcash.scene.register to javafx.fxml;
    opens rebelalliance.smartcash.scene to javafx.fxml;
}
