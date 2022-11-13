package pageobject.forms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import constants.CommonConstant;
import org.openqa.selenium.By;
import utils.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class TestTableForm extends Form {
    private final String testsStartTimeListXPath = "//div[contains(@class,'container')]/child::div[contains(@class, 'panel')][2]//tr/td[4]";
    private final String testsNameListXPath = "//div[contains(@class,'container')]/child::div[contains(@class, 'panel')][2]//tr/td[1]";
    private final String testNameXPath = "//div[contains(@class,'container')]/child::div[contains(@class,'panel')][2]//tr/td/a[contains(text(),'%s')]";
    private List<ILabel> testsStartTimeList;
    private List<ILabel> testsNameList;
    private ILabel lblTestName;
    private final IButton btnAllTests = getElementFactory().getButton(By.xpath("//div[contains(@class,'container')]/ul/li[1]/a[@href]"), "All tests tab");

    public TestTableForm() {
        super(By.xpath("//table//parent::div[contains(@class,'panel-default')]"), "Test Table form");
    }

    public int getOpenedProjectId(){
        btnAllTests.state().waitForClickable();
        String id = btnAllTests.getAttribute("href");
        return Integer.parseInt(id.replace(CommonConstant.PROJECT_TESTS_WITHOUT_ID_URL,""));
    }

    public boolean idDisplayedTest(String testName){
        lblTestName = getElementFactory().getLabel(By.xpath(String.format(testNameXPath,testName)),"Test name label");
        return lblTestName.state().waitForDisplayed();
    }

    public void openTest(String testName){
        lblTestName = getElementFactory().getLabel(By.xpath(String.format(testNameXPath,testName)),"Test name label");
        lblTestName.click();
    }

    public List<String> getTestsStartTimeList(){
        testsStartTimeList = getElementFactory().findElements(By.xpath(testsStartTimeListXPath), ElementType.LABEL);
        return testsStartTimeList.stream().map(IElement::getText).collect(Collectors.toList());
    }

    public List<String> getTestsNameList(){
        testsNameList = getElementFactory().findElements(By.xpath(testsNameListXPath), ElementType.LABEL);
        return testsNameList.stream().map(IElement::getText).collect(Collectors.toList());
    }
    public void makeScreenShot(){
        FileUtil.makeAndSaveScreenShot();
    }
}
