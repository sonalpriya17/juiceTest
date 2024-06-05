package gradle.junit.selenium.baseTest;

import gradle.junit.selenium.Customer;
import gradle.junit.selenium.util.Env;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BaseTestUI implements BaseTest {
    protected static WebDriver driver;
    protected static Customer customer;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        if ("host.docker.internal".equals(address)) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("goog:loggingPrefs", Map.of(
                    "browser", "ALL",
                    "driver", "ALL"
            ));
            URL remoteAddress = null;
            try {
                remoteAddress = new URL(Env.get("SELENIUM_HUB_URL"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            driver = new RemoteWebDriver(remoteAddress, options);
        } else {
            driver = new ChromeDriver();
        }

        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        customer = new Customer.Builder()
                .setEmail("sonalpriya172@gmail.com")
                .setPassword("KritiKrisha26")
                .setSecurityAnswer("gita").build();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}