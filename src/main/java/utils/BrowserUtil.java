package utils;

import aquality.selenium.browser.AqualityServices;
import constants.CommonConstant;

public class BrowserUtil {

    public static void switchToIframe() {
        AqualityServices.getBrowser().getDriver().switchTo().frame(CommonConstant.ADD_PROJECT_IFRAME_ID);
    }

    public static void closeAlertByJSMethod() {
        AqualityServices.getBrowser().executeScript(ConfigUtil.getTestData("closePopUp"));
    }
}
