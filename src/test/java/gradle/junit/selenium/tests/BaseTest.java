package gradle.junit.selenium.tests;

import gradle.junit.selenium.Customer;
import gradle.junit.selenium.util.DriverSetup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected static WebDriver driver;
    static Customer customer;


/*    @BeforeAll
    public static void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        customer = new Customer.Builder()
                .setEmail("sonalpriya172@gmail.com")
                .setPassword("KritiKrisha26")
                .setSecurityAnswer("gita").build();
    }*/

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }
}