import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected Browser browser;

    @BeforeMethod
    public void setUp(){
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }
}
