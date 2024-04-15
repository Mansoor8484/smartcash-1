package rebelalliance.smartcash.database;

import java.util.HashMap;

public class DatabaseManager {
    private final HashMap<String, User> registeredUsers;

    public DatabaseManager() {
        this.registeredUsers = new HashMap<>();
    }

    public void registerUser(String email, String password) {
        this.registeredUsers.put(email, new User(email, password));
    }

    public void registerUser(String email, String password, String mfaSecret) {
        this.registeredUsers.put(email, new User(email, password, mfaSecret));
    }

    public void enableMfa(User user, String mfaSecret) {
        user.mfaSecret = mfaSecret;
    }

    public void disableMfa(User user) {
        user.mfaSecret = null;
    }

    public User login(String email, String password) {
        User user = this.registeredUsers.get(email);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
