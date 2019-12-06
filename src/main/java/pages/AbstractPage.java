package pages;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage extends PageObject{
    public AbstractPage(WebDriver driver) {
        super(driver);
    }

    public void refreshPage(){
        getDriver().navigate().refresh();
    }

    public boolean checkIfElementIsPresentNoTimeout(WebElement webElement) {
        try {
            checkIfElementExistsNoTimeout(webElement);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public void refreshPageClearCache() {
        Actions action = new Actions(getDriver());
        action.sendKeys(Keys.chord(Keys.LEFT_CONTROL, Keys.F5)).perform();
    }

    public void checkIfElementExistsNoTimeout(WebElement webElement) {
        // Should be read from the command line (i.e. Value passed to maven at runtime)
        int defaultWait = 3900;
        // Remove implicit wait.
        getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(500, TimeUnit.MILLISECONDS)
                .pollingEvery(490, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.visibilityOf(webElement));

        // Restore implicit wait.
        getDriver().manage().timeouts().implicitlyWait(defaultWait, TimeUnit.MILLISECONDS);
    }

    public boolean waitAndCheckMaybeElementWillAppear(WebElement webElement, int seconds) {
        try {
            waitForElementInSeconds(webElement, seconds);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public boolean verifyCheckboxChecked(WebElement webElement) {
        try {
            webElement.isSelected();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public void waitForElementInSeconds(WebElement webElement, int timeoutSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
                .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void checkWebElementPresentByXpath(String locatorKey) {
        boolean flag;
        try {
            getDriver().findElement(By.xpath(locatorKey));
            flag = true;
        } catch (NoSuchElementException e) {
            flag = false;
        }

        Assert.assertTrue("Element was not present", flag);
    }

    public void selectElementFromDropdownListByValue(WebElement list, String value) {
        Select dropDownList = new Select(list);
        dropDownList.selectByValue(value);
    }

    public void selectElementFromDropdownListByIndex(WebElement list, int index) {
        Select dropDownList = new Select(list);
        dropDownList.selectByIndex(index);
    }

    public void scrollToElement(WebElement myElement) {
        waitABit(1000);
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0," + myElement.getLocation().y + ")");
        waitABit(1000);
    }

    public void checkExternalPageBasedOnLink(WebElement linkToBeClicked, WebElement externalPageTitle) {
        String parentHandle = getDriver().getWindowHandle();

        waitAndCheckMaybeElementWillAppear(linkToBeClicked, 5);
        linkToBeClicked.click();

        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }

        Serenity.takeScreenshot();
        Assert.assertTrue("External page is not visible",
                waitAndCheckMaybeElementWillAppear(externalPageTitle, 5));

        getDriver().close();
        getDriver().switchTo().window(parentHandle);
    }

    public void setFutureDate(String numberOfDaysIntoTheFuture, WebElement dayInput, WebElement monthInput, WebElement yearInput) {
        SimpleDateFormat formattedDate = new SimpleDateFormat("ddMMyyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, Integer.parseInt(numberOfDaysIntoTheFuture));  // number of days to add
        String myFutureDate = formattedDate.format(c.getTime());
        String dayToBeSet = myFutureDate.substring(0,2);
        String monthToBeSet = myFutureDate.substring(2,4);
        String yearToBeSet = myFutureDate.substring(4,8);

        waitAndCheckMaybeElementWillAppear(dayInput, 5);
        dayInput.clear();
        dayInput.sendKeys(dayToBeSet);

        waitAndCheckMaybeElementWillAppear(monthInput, 5);
        monthInput.clear();
        monthInput.sendKeys(monthToBeSet);

        waitAndCheckMaybeElementWillAppear(yearInput, 5);
        yearInput.clear();
        yearInput.sendKeys(yearToBeSet);
    }

    public void checkMaxCharactersOnInputField(String selector, int max) {
        WebElement elementToBeChecked = getDriver().findElement(By.cssSelector(selector));
        int maxValue = Integer.parseInt(elementToBeChecked.getAttribute("maxlength"));

        Assert.assertEquals("Max value is not correct", maxValue, max);
    }

    public void clickBreadCrumbs(WebElement tabToBeClicked, WebElement elementToBeChecked) {
        waitAndCheckMaybeElementWillAppear(tabToBeClicked, 5);
        tabToBeClicked.click();

        Assert.assertTrue("Current page was not shown",
                waitAndCheckMaybeElementWillAppear(elementToBeChecked, 5));
    }
}
