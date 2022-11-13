import models.ProjectTest;
import pageobject.TestPage;
import queries.ApiRequest;
import aquality.selenium.core.logging.Logger;
import com.google.common.collect.Ordering;
import constants.CommonConstant;
import constants.DataBaseConstant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.MainPage;
import pageobject.ProjectPage;
import queries.DataBaseRequest;
import utils.*;

import java.util.Base64;
import java.util.List;

import static queries.ApiRequest.RESPONSE_JSON;

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
        Assert.assertEquals(RESPONSE_JSON.getStatusCode(), HttpStatus.SC_OK,
                String.format("status code is not %d status is:%d", HttpStatus.SC_OK, RESPONSE_JSON.getStatusCode()));

        browser.goTo(url);
        Assert.assertTrue(mainPage.state().isDisplayed(), "Main Page has not been loaded");

        Logger.getInstance().info("Send cookie with variant token");
        CookieUtils.addCookie(new Cookie(cookieTokenParam, token));
        browser.refresh();
        softAssert.assertTrue(mainPage.getFooter().getVersion().contains(variant), "Version not equals variant");

        Logger.getInstance().info(String.format("Opening project %s", project));
        mainPage.getProjectList().clickProject(project);
        browser.waitForPageToLoad();
        Assert.assertTrue(projectPage.state().isDisplayed(),String.format("%s project page nas not been loaded", project));
        Assert.assertTrue(Ordering.natural().reverse().isOrdered(projectPage.getTestTable().getTestsStartTimeList()), "Tests aren't sorted by descending date");

        String[][] arrayTableFromDB = DataBaseUtils.selectTable(String.format(DataBaseConstant.SELECT_TESTS_LIST_QUERY, project));
        List<String> testNameList = projectPage.getTestTable().getTestsNameList();
        softAssert.assertTrue(CompareUtil.isEqual(testNameList, arrayTableFromDB), "Tests list from GUI doesn't equal tests list from DB" );

        Logger.getInstance().info("Going on Project page and create a new project..");
        browser.goBack();
        mainPage.getProjectList().clickAddProject();
        mainPage.getAddProjectForm().switchToIframe();
        Assert.assertTrue(mainPage.getAddProjectForm().state().waitForExist(), "Add form has not been load");

        String randomProjectName = RandomStringUtils.randomAlphabetic(CommonConstant.RANDOM_OBJ_LENGTH);
        mainPage.getAddProjectForm().inputProjectNameAndSave(randomProjectName);
        Assert.assertTrue(mainPage.getAddProjectForm().isDisplayedAlert(), "Success Alert doesn't displayed");
        mainPage.getAddProjectForm().closeAlertByJSMethod();
        Assert.assertTrue(mainPage.getAddProjectForm().isNotDisplayed(), "Success alert actually present");
        browser.refresh();
        Assert.assertTrue(mainPage.getProjectList().checkNewProject(randomProjectName), String.format("%s missing on the list", randomProjectName));


        Logger.getInstance().info(String.format("Opening %s project and sending test with attachments to database", randomProjectName));
        mainPage.getProjectList().clickProject(randomProjectName);
        byte[] screenshotBytes = browser.getScreenshot();
        projectPage.getTestTable().saveScreenShot(screenshotBytes);
        ProjectTest createTest = new DataBaseRequest().sendNewTest(projectPage.getTestTable().getOpenedProjectId());
        DataBaseUtils.insertTest(createTest);
        Assert.assertTrue(projectPage.getTestTable().idDisplayedTest(createTest.getName()), "Test wasn't added to project");

        Logger.getInstance().info("Opening created test");
        projectPage.getTestTable().openTest(createTest.getName());
        TestPage testPage = new TestPage(createTest.getName());
        Assert.assertTrue(testPage.state().isDisplayed(),"Test page hasn't been loaded");
        softAssert.assertEquals(testPage.getProjectName(), randomProjectName, "Test project name in UI doesn't match with test project name where was added");
        softAssert.assertEquals(testPage.getTestName(), createTest.getName(), "Test name in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getMethodName(), createTest.getMethodName(), "Test method name in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getStatusId(), createTest.getStatusId(), "Test status in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getStartTime(), createTest.getStartTime().toString(), "Test start time in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getEndTime(), createTest.getEndTime().toString(), "Test end time in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getEnv(), createTest.getEnv(), "Test environment in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getBrowserName(), createTest.getBrowser(), "Test browser in UI doesn't match with added test");
        softAssert.assertEquals(testPage.getScreenshotLink(), Base64.getEncoder().encodeToString(screenshotBytes),
                "Test screenshot in UI doesn't match with added attachment");
        softAssert.assertAll();
    }
}
