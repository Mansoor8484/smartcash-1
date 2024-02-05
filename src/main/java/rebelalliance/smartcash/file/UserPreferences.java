package rebelalliance.smartcash.file;

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

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public String getString(String key) {
        return this.properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(this.properties.getProperty(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.properties.getProperty(key));
    }

    public void setString(String key, String value) {
        this.properties.setProperty(key, value);
        this.save();
    }

    public void setInt(String key, int value) {
        this.properties.setProperty(key, Integer.toString(value));
        this.save();
    }

    public void setBoolean(String key, boolean value) {
        this.properties.setProperty(key, Boolean.toString(value));
        this.save();
    }
}
