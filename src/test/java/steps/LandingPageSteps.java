package steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import org.apache.xpath.operations.String;
import pages.LandingPage;

public class LandingPageSteps extends AbstractSteps<LandingPage> {

    private static final long serialVersionUID = 1L;

    public LandingPageSteps(Pages pages) {
        super(pages, LandingPage.class);
    }

    @Step("Send postal code {0}")
    public void sendPostalCode(String postalCode) { currentPage().sendPostalCode("12345"); }
}
