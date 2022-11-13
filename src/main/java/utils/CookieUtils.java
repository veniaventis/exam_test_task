package utils;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.Cookie;

public class CookieUtils {

    public static void addCookie(Cookie cookie) {
        AqualityServices.getBrowser().getDriver().manage().addCookie(cookie);
    }
}
