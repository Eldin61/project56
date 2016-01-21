package main.java.com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigManager {
    Properties properties = new Properties();
    OutputStream output = null;

    public ConfigManager() {
        try {
            output = new FileOutputStream("config.properties");

            properties.setProperty("db_name", "jdbc:postgresql://localhost:5432/citygis");
            properties.setProperty("db_user", "postgres");
            properties.setProperty("db_password", "forthe12");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
