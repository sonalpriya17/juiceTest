package gradle.junit.selenium.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import gradle.junit.selenium.util.LoggerUtil;
import org.slf4j.Logger;

import java.io.IOException;

public class TestHelper {
    private static final Logger logger = LoggerUtil.getLogger();

    public static String getJsonString(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(o);
        } catch (IOException e) {
            logger.error("Error converting object to JSON string", e);
        }
        return "";
    }
    public static void wait(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            logger.error("Thread interrupted while waiting", e);
        }
    }
}
