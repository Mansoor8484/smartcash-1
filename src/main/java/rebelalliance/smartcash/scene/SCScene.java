package rebelalliance.smartcash.scene;


public enum SCScene {
    OVERVIEW("overview"),
    REGISTER("register"),
    TRANSACTIONS("transactions");

    final String path;

    SCScene(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
