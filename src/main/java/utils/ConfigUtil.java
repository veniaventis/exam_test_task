package utils;

import aquality.selenium.core.utilities.JsonSettingsFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigUtil {

    public static String getConfidentialData(String key) {
        return new JsonSettingsFile("confidentialData.json").getValue(String.format("/%s", key)).toString();
    }

    public static String getSettingsData(String key) {
        return new JsonSettingsFile("settings.json").getValue(String.format("/%s", key)).toString();
    }

    public static String getTestData(String key){
        return new JsonSettingsFile("testData.json").getValue(String.format("/%s", key)).toString();
    }
    public static File getResourceByFileName(String filePath) {
        return Paths.get(filePath).toFile();
    }
}
