package pageobject;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectPage extends Form {
    public ProjectPage(String project) {
        super(By.xpath(String.format("//div[contains(@class,'main-container')]//child::li[contains(text(),'%s')]", project)), String.format("%s project", project));
    }
}
