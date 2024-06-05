package gradle.junit.selenium.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Env {
    private Dotenv dotenv;

    private static Env singleton;

    private static Env instance() {
        if (Env.singleton == null) {
            Env.singleton = new Env();
        }

        return Env.singleton;
    }

    private Env() {
        dotenv = Dotenv.load();
    }

    public static String get(String key) {
        return instance().dotenv.get(key);
    }
}