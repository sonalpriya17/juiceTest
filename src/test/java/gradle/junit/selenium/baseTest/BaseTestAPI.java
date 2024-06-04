package gradle.junit.selenium.baseTest;

import gradle.junit.selenium.Customer;
import org.junit.jupiter.api.BeforeAll;

public class BaseTestAPI implements BaseTest {
    protected static Customer customer;
    /*protected static String address = System.getProperty("juiceShopHost", "localhost");
    protected static String port = "3000";
    protected static String baseUrl = String.format("http://%s:%s", address, port);*/

    @BeforeAll
    public static void setUp() {
        customer = new Customer.Builder()
                .setEmail("sonalpriya172@gmail.com")
                .setPassword("KritiKrisha26")
                .setSecurityAnswer("gita").build();

    }


}
