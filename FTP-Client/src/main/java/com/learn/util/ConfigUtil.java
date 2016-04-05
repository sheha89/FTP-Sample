package com.learn.util;

import com.google.common.base.Optional;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static final Properties applicationProperties = new Properties();

    static {
        try {
            applicationProperties.load(new FileReader(ConfigUtil.class.getClassLoader().getResource("application.properties").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String key) {
        return applicationProperties.getProperty(key);
    }

    public static Optional<String> getApplicationConfig(String key) {
        return Optional.fromNullable(applicationProperties.getProperty(key));
    }
}

