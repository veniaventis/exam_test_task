package pageobject.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utils.FileUtil;

public class ProjectListForm extends Form {
    private final ILabel projectList = getElementFactory().getLabel(By.xpath("//div[contains(@class,'list-group')]"), "Project List");
    private final IButton addProjectBtn = getElementFactory().getButton(By.xpath("//button[contains(@data-target,'addProject')]"), "Add button");
    private final String projectBtnXpath  = "//div[contains(@class,'list-group')]//child::a[contains(@class,'list-group-item') and contains(text(),'%s')]";
    private IButton projectBtn;
    public ProjectListForm() {
        super(By.xpath("//button[contains(@data-target,'addProject')]//ancestor::div[contains(@class,'panel-default')]"), "Project List Form");
    }

    public void clickAddProject() {
        addProjectBtn.click();
    }

    public void clickProject(String projectName) {
        projectBtn = getElementFactory().getButton(By.xpath(String.format(projectBtnXpath, projectName)), String.format("%s project", projectName));
        projectBtn.click();
    }

    public boolean checkNewProject(String newProject) {
        return projectList.getText().contains(newProject);
    }
}
