package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
//    Luu toan bo key va value do tu file config.properties
//    Vi sao dung properties?
//    1. La class co san trong Java
//    2. De doc file cau hinh dang key=value
//    3. Don gian de su dung
    private static final Properties CONFIG = loadConfig();

    private static Properties loadConfig() {
        Properties p = new Properties();
        try (InputStream in = ConfigUtils.class.getResourceAsStream("/config.properties")){
                if(in != null) {
                    p.load(in);
                }
            }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public static String getLoginUrl() {
        return CONFIG.getProperty("login.url", "");
    }

    public static String getUsername() {
        return CONFIG.getProperty("login.username", "Admin");
    }

    public static String getPassword() {
        return CONFIG.getProperty("login.password", "admin123");
    }
}
