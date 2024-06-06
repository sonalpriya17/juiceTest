package gradle.junit.selenium.baseTest;

import gradle.junit.selenium.util.EnvUtil;

public interface BaseTest {
    String baseUrl = String.format("http://%s:%s", EnvUtil.get("TARGET_HOST"), EnvUtil.get("TARGET_PORT"));
    static void setUp() {}

}