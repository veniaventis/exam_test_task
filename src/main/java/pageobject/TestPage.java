package pageobject;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import constants.CommonConstant;
import org.openqa.selenium.By;

public class TestPage extends Form {
    private final String testFieldsXPath = "//div[contains(@class,'col-md-4')]//div[contains(@class,'panel-default')][1]//div[contains(@class,'list-group-item')]";
    private final ILabel projectNameLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[1]/p[contains(@class,'list-group-item-text')]"), "Test's project name");
    private final ILabel nameLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[2]/p[contains(@class,'list-group-item-text')]"), "Test name");
    private final ILabel methodNameLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[3]/p[contains(@class,'list-group-item-text')]"), "Test's method name");
    private final ILabel statusLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[4]/p[contains(@class,'list-group-item-text')]"), "Test's status");
    private final ILabel startTimeLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[5]/p[contains(@class,'list-group-item-text')][1]"), "Test's start time");
    private final ILabel endTimeLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[5]/p[contains(@class,'list-group-item-text')][2]"), "Test's end time");
    private final ILabel environmentLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[6]/p[contains(@class,'list-group-item-text')]"), "Test's environment");
    private final ILabel browserLbl = getElementFactory().getLabel(By.xpath(testFieldsXPath + "[7]/p[contains(@class,'list-group-item-text')]"), "Test's browser");
    private final ILink screenshotLnk = getElementFactory().getLink(By.xpath("//div[contains(@class,'col-md-8')]//table[@class='table']//td/a[1]"), "Screenshot link");


    public TestPage(String testName) {
        super(By.xpath(String.format("//div[contains(@class,'list-group')]//child::p[contains(text(),'%s')]", testName)), "Test page");
    }

    public String getProjectName() {
        return projectNameLbl.getText();
    }

    public String getTestName() {
        return nameLbl.getText();
    }

    public String getMethodName() {
        return methodNameLbl.getText();
    }

    public int getStatusId() {
        return switch (statusLbl.getText()) {
            case "Passed" -> 1;
            case "Failed" -> 2;
            case "Skipped" -> 3;
            default -> 0;
        };
    }

    public String getStartTime() {
        return startTimeLbl.getText().replace(CommonConstant.START_TIME_UI_TEXT_REPLACEMENT, "");
    }

    public String getEndTime() {
        return endTimeLbl.getText().replace(CommonConstant.END_TIME_UI_TEXT_REPLACEMENT, "");
    }

    public String getEnv() {
        return environmentLbl.getText();
    }

    public String getBrowserName() {
        return browserLbl.getText();
    }

    public String getScreenshotLink() {
        return screenshotLnk.getHref().replace(CommonConstant.IMAGE_HREF_REPLACEMENT, "");
    }
}
