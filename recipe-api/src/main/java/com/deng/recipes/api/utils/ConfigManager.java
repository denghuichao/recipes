package com.deng.recipes.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class ConfigManager {
    private static final String CLASSPATH_CONFIG_PATH = "/app.properties";

    private Properties properties;

    public static ConfigManager instance() {
        return ConfigManagerHolder.CONFIG_MANAGER_INSTANCE;
    }

    private ConfigManager() {
        properties = new Properties();

        InputStream inputStream = ConfigManager.class.getResourceAsStream(CLASSPATH_CONFIG_PATH);
        try {
            properties.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
        }
    }

    public String getProperty(String key) {
        return getProperty(key, null);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager CONFIG_MANAGER_INSTANCE = new ConfigManager();
    }
}
