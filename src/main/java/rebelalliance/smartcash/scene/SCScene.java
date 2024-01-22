package rebelalliance.smartcash.scene;

public enum SCScene {
    OVERVIEW("overview", "Overview"),
    REGISTER("register", "Register"),
    TRANSACTIONS("transactions", "Transactions");

    final String path;
    final String title;

    SCScene(String path, String title) {
        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return this.path;
    }

    public String getTitle() {
        return this.title;
    }
}
