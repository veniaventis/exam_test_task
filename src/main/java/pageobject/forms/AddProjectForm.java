package pageobject.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import constants.CommonConstant;
import org.openqa.selenium.By;
import utils.ConfigUtil;

public class AddProjectForm extends Form {
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

    public boolean isNotDisplayed() {
        return this.state().waitForNotDisplayed();
    }

    public boolean isDisplayedAlert() {
        return successAlert.state().waitForDisplayed();
    }
}
