import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigUtil;

public abstract class BaseTest {
    protected Browser browser;

    @BeforeMethod
    public void setUp(){
        String url = ConfigUtil.getSettingsData("uiUrl");
        browser = AqualityServices.getBrowser();
        browser.maximize();
        browser.goTo(url);
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }
}
