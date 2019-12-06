package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage extends AbstractPage {
    LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "name='q'")
    private WebElement inputField;


    public void sendPostalCode(String postalCode) {
	inputField.sendKeys(postalCode);
    }
}
