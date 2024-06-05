package gradle.junit.selenium.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    public static Logger getLogger() {
        String className = new Throwable().getStackTrace()[1].getClassName();
        return LoggerFactory.getLogger(className);
    }
}