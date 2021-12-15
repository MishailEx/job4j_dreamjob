package ru.job4j.dream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class ParseProperties {
    private static ParseProperties parseProperties;
    private static final Properties CFG = new Properties();

    private ParseProperties() {
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        ParseProperties.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            CFG.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String parse(String str) {
        if (parseProperties == null) {
            parseProperties = new ParseProperties();
        }
        return CFG.getProperty(str);
    }
}
