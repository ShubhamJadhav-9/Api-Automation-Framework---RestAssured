package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop = new Properties();

    static {
        try {
            InputStream is = ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config.properties");
            if (is == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUri() {
        return prop.getProperty("base.uri");
    }
}