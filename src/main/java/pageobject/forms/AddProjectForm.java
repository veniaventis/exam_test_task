package pageobject.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AddProjectForm extends Form {
    private final ILabel lblSuccessAlert = getElementFactory().getLabel(By.xpath("//form[@id='addProjectForm']//div[contains(@class,'alert-success')]"), "Success alert");
    private final ILabel successAlert = getElementFactory().getLabel(By.xpath("//form[@id='addProjectForm']//child::div[contains(@class,'alert-success')]"), "Success alert");
    private final ITextBox projectNameInput = getElementFactory().getTextBox(By.id("projectName"), "Input project name Field");
    private final IButton saveProjectBtn = getElementFactory().getButton(By.xpath("//button[contains(@class,'btn-primary') and contains(@type,'submit')]"), "Save project button");

    public AddProjectForm() {
        super(By.id("addProjectForm"), "Add Project Form");
    }



    public void inputProjectNameAndSave(String projectName) {
        projectNameInput.clearAndType(projectName);
        saveProjectBtn.click();
    }

    public boolean isDisplayedAlert() {
        return successAlert.state().waitForDisplayed();
    }
    public void switchToIframe() {
        AqualityServices.getBrowser().getDriver().switchTo().frame("addProjectFrame");
    }
}
