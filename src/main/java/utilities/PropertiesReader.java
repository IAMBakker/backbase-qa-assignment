package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static String getProperty(String propertyName) {
        try (InputStream input = PropertiesReader.class.getResourceAsStream("/config.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            return prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
