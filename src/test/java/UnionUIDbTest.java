import api.ApiRequest;
import aquality.selenium.core.logging.Logger;
import constants.CommonConstant;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.MainPage;
import pageobject.ProjectPage;
import utils.ConfigUtil;
import utils.CookieUtils;

public class UnionUIDbTest extends BaseTest {
    private final String url = String.format(ConfigUtil.getSettingsData("uiUrl"),ConfigUtil.getConfidentialData("uiLogin"),ConfigUtil.getConfidentialData("uiPassword"));
    private final String variant = ConfigUtil.getTestData("variant");
    private final String project = ConfigUtil.getTestData("projectName");
    private final String cookieTokenParam = CommonConstant.TOKEN_COOKIE_PARAM;
    private final SoftAssert softAssert = new SoftAssert();
    private final MainPage mainPage = new MainPage();
    private final ProjectPage projectPage = new ProjectPage(project);
    @Test
    public void uiDbTest() {
        Logger.getInstance().info("Getting variant token");
        String token = ApiRequest.getToken(variant);
        softAssert.assertNotNull(token,"Failed to get token");

        browser.goTo(url);
        Assert.assertTrue(mainPage.state().isDisplayed(), "Project Page has not been loaded");

        Logger.getInstance().info("Send cookie with variant token");
        CookieUtils.addCookie(new Cookie(cookieTokenParam, token));
        browser.refresh();
        softAssert.assertTrue(mainPage.getFooter().getVersion().contains(variant), "Version not equals variant");

        Logger.getInstance().info(String.format("Opening project %s", project));
        mainPage.getProjectList().clickProject(project);
        browser.waitForPageToLoad();
        Assert.assertTrue(projectPage.state().isDisplayed(),String.format("%s project page nas not been loaded", project));

    }

}
