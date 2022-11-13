package utils;

import aquality.selenium.core.logging.Logger;

import java.util.List;

public class CompareUtil {
    public static boolean isEqual(List<String> list, String[][] array) {
        Logger.getInstance().debug("Comparing list and array");
        int count = Math.max(list.size(), array.length);
        for (int i = 0; i < count; i++) {
            if (list.get(i).equals(array[i][0])) {
            } else {
                return false;
            }
        }
        return true;
    }
}
