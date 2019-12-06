package tests;

import common.Constants;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.*;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.apache.xpath.operations.String;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import requirements.Application;
import steps.LandingPageSteps;


@Story(Application.Test.class)
@RunWith(SerenityParameterizedRunner.class)
//@UseTestDataFrom(value = Constants.BASE_DIR + "Test01.csv", separator = ',')

public class Test01 {

    @Managed(uniqueSession = false)
    private WebDriver driver;

    @ManagedPages(defaultUrl = Constants.URL)
    private Pages pages;

    @Steps
    private LandingPageSteps landingPageSteps;

    private String testKey;

    @Test
    @Title("Test01 - Primul test")
    public void test01() {
        landingPageSteps.sendPostalCode(testKey);
    }
}
