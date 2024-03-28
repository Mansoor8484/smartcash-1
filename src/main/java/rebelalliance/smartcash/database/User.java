package rebelalliance.smartcash.database;

public class User {
    String email;
    String password;
    String mfaSecret;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.mfaSecret = null;
    }

    public boolean hasMfaEnabled() {
        return mfaSecret != null;
    }
}
