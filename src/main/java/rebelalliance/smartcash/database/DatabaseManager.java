package rebelalliance.smartcash.database;

import java.util.HashMap;

public class DatabaseManager {
    private final HashMap<String, String> registeredUsers;

    public DatabaseManager() {
        this.registeredUsers = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        this.registeredUsers.put(username, password);
    }

    public boolean isLoginSuccessful(String email, String password) {
        String fetchedPassword = this.registeredUsers.get(email);
        if(fetchedPassword == null) {
            return false;
        }
        return password.equals(fetchedPassword);
    }
}
