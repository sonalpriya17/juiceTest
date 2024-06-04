package gradle.junit.selenium.baseTest;

import gradle.junit.selenium.Customer;

import java.net.MalformedURLException;

public interface BaseTest {
    String address = System.getProperty("juiceShopHost", "localhost");
    String port = "3000";
    String baseUrl = String.format("http://%s:%s", address, port);

    static void setUp() {}

}