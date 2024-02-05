package rebelalliance.smartcash.file.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class UserPreferences {
    private String filePath;
    private Properties properties;

    public UserPreferences(String fileName) {
        this.filePath = System.getProperty("user.home") + "\\" + fileName + ".properties";
        File file = new File(this.filePath);

        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            this.properties = new Properties();
            properties.load(fileInputStream);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.filePath);
            this.properties.store(fileOutputStream, null);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean containsKey(UserPreference key) {
        return this.properties.containsKey(key.getKey());
    }

    public String getString(UserPreference preference) {
        return this.properties.getProperty(preference.getKey());
    }

    public int getInt(UserPreference preference) {
        return Integer.parseInt(this.properties.getProperty(preference.getKey()));
    }

    public boolean getBoolean(UserPreference preference) {
        return Boolean.parseBoolean(this.properties.getProperty(preference.getKey()));
    }

    public void setString(UserPreference preference, String value) {
        this.properties.setProperty(preference.getKey(), value);
        this.save();
    }

    public void setInt(UserPreference preference, int value) {
        this.properties.setProperty(preference.getKey(), Integer.toString(value));
        this.save();
    }

    public void setBoolean(UserPreference preference, boolean value) {
        this.properties.setProperty(preference.getKey(), Boolean.toString(value));
        this.save();
    }
}
