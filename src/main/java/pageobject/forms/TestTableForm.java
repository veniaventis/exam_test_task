package pageobject.forms;

import aquality.selenium.elements.Attributes;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import constants.CommonConstant;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class TestTableForm extends Form {
    private final String testNameXPath = "//table[contains(@class,'table')]//child::td[%s]/a[contains(text(),'%s')]";
    private final String tableXpath = "//table[contains(@class,'table')]//child::td[%s]";
    private List<ILabel> testsStartTimeList;
    private List<ILabel> testsNameList;
    private ILabel testNameLbl;
    private final IButton allTestsBtn = getElementFactory().getButton(By.xpath("//div[contains(@class,'container')]/ul/li[1]/a[@href]"), "All tests tab");

    public TestTableForm() {
        super(By.xpath("//table//parent::div[contains(@class,'panel-default')]"), "Test Table form");
    }

    public int getOpenedProjectId() {
        allTestsBtn.state().waitForClickable();
        String id = allTestsBtn.getAttribute(Attributes.HREF.toString());
        return Integer.parseInt(id.replace(CommonConstant.PROJECT_TESTS_WITHOUT_ID_URL, ""));
    }


    public boolean idDisplayedTest(String testName) {
        testNameLbl = getElementFactory().getLabel(By.xpath(String.format(testNameXPath, TestTableTitles.TEST_NAME.getTitle(), testName)), "Test name label");
        return testNameLbl.state().waitForDisplayed();
    }

    public void openTest(String testName) {
        testNameLbl = getElementFactory().getLabel(By.xpath(String.format(testNameXPath, TestTableTitles.TEST_NAME.getTitle(), testName)), "Test name label");
        testNameLbl.click();
    }

    public List<String> getTestsStartTimeList() {
        testsStartTimeList = getElementFactory().findElements(By.xpath(String.format(tableXpath, TestTableTitles.LATEST_TEST_START_TIME.getTitle())), ElementType.LABEL);
        return testsStartTimeList.stream().map(IElement::getText).collect(Collectors.toList());
    }

    public List<String> getTestsNameList() {
        testsNameList = getElementFactory().findElements(By.xpath(String.format(tableXpath, TestTableTitles.TEST_NAME.getTitle())), ElementType.LABEL);
        return testsNameList.stream().map(IElement::getText).collect(Collectors.toList());
    }
}
