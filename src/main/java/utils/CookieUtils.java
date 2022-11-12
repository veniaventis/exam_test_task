package utils;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.Cookie;

public class CookieUtils {

    public static void addCookie(Cookie cookie) {
        AqualityServices.getBrowser().getDriver().manage().addCookie(cookie);
    }

    public static void deleteCookie(String cookieName) {
        AqualityServices.getBrowser().getDriver().manage().deleteCookieNamed(cookieName);
    }
}
