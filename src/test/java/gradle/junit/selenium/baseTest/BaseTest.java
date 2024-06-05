package gradle.junit.selenium.baseTest;

import gradle.junit.selenium.Customer;
import gradle.junit.selenium.util.Env;

import java.net.MalformedURLException;

public interface BaseTest {
//    String address = System.getProperty("juiceShopHost", "localhost");
    String address = Env.get("TARGET_HOST");
    String port = Env.get("TARGET_PORT");
    String baseUrl = String.format("http://%s:%s", address, port);

    static void setUp() {}

}