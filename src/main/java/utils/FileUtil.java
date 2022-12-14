package utils;

import aquality.selenium.core.logging.Logger;
import constants.CommonConstant;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static void saveScreenShot(byte[] screenshotBytes) {
        try (FileOutputStream fos = new FileOutputStream(CommonConstant.TEST_SCREENSHOT_PATH);
        BufferedOutputStream bos = new BufferedOutputStream(fos) ){
            Logger.getInstance().debug("Saving screenshot to .png file");
            bos.write(screenshotBytes);
        } catch (IOException e) {
            Logger.getInstance().debug("Screenshot cannot be save", e);
        }
    }
}
