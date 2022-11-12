package pageobject.forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FooterForm extends Form {

    private final ILabel lblVersion  = getElementFactory().getLabel(By.xpath("//footer//p[contains(@class,'footer-text')]//child::span"),"Version Label");

    public FooterForm() {
        super(By.xpath("//footer[contains(@class,'footer')]"), "Footer");
    }

    public String getVersion() {
        return lblVersion.getText();
    }
}
