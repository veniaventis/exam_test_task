package utils;

import aquality.selenium.core.logging.Logger;
import constants.CommonConstant;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static void saveScreenShot(byte[] screenshotBytes) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            Logger.getInstance().debug("Saving screenshot to .png file");
            fos = new FileOutputStream(CommonConstant.TEST_SCREENSHOT_PATH);
            bos = new BufferedOutputStream(fos);
            bos.write(screenshotBytes);
        } catch (IOException e) {
            Logger.getInstance().debug("Screenshot cannot be save", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
