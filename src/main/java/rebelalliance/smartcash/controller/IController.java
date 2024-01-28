package rebelalliance.smartcash.controller;

public interface IController {
    /**
     * Called when the controller is initialized.
     */
    void init();

    /**
     * Should be called when data is updated or filters are updated.
     */
    void update();
}
