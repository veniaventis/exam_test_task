package pageobject;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import pageobject.forms.AddProjectForm;
import pageobject.forms.FooterForm;
import pageobject.forms.ProjectListForm;

public class MainPage extends Form {

    public MainPage() {
        super(By.xpath("//div[contains(@class,'panel-default')]"), "Project Page");
    }

    public FooterForm getFooter() {
        return new FooterForm();
    }

    public ProjectListForm getProjectList() {
        return new ProjectListForm();
    }

    public AddProjectForm getAddProjectForm() {
        return new AddProjectForm();
    }
}
