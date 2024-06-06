package gradle.junit.selenium.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtil {
    private Dotenv dotenv;
    private static EnvUtil singleton;
    private EnvUtil() {
        dotenv = Dotenv.load();
    }
    private static EnvUtil instance() {
        if (EnvUtil.singleton == null) {
            EnvUtil.singleton = new EnvUtil();
        }
        return EnvUtil.singleton;
    }

    public static String get(String key) {
        return instance().dotenv.get(key);
    }
}