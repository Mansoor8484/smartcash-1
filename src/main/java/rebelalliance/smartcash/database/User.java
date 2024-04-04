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

    public User(String email, String password, String mfaSecret) {
        this.email = email;
        this.password = password;
        this.mfaSecret = mfaSecret;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMfaSecret() {
        return mfaSecret;
    }

    public boolean hasMfaEnabled() {
        return mfaSecret != null;
    }
}
