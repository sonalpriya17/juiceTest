package gradle.junit.selenium.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import gradle.junit.selenium.util.LoggerUtil;
import org.slf4j.Logger;

public class DriverSetup {

    private static final Logger logger = LoggerUtil.getLogger();

    public static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        logger.info("ChromeDriver is set up.");
        return new ChromeDriver();
    }
}