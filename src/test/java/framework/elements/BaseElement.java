package framework.elements;
import framework.BaseEntity;
import framework.Browser;
import framework.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseElement extends BaseEntity {
    private static final String HIGHLIGHT_BOARDER_JS = "arguments[0].style.border='3px solid red'";
    private static final String LINK = "link=";
    private static final String ID = "id=";
    private static final String CSS = "css=";

    private static final int TIMEOUT_WAIT_0 = 0;

    protected static Browser browser = Browser.getInstance();
    protected WebElement element;
    protected List<WebElement> elements;
    protected By locator;

    /**
     * @uml.property name="name"
     */
    protected String name;


    public BaseElement(final By locator) {
        this.locator = locator;
    }

    /**
     * The main constructor
     * @param loc By Locator
     * @param nameOf Output in logs
     */
    protected BaseElement(final By loc, final String nameOf) {
        locator = loc;
        name = nameOf;
    }

    /**
     * easy adapting from Selenium RC locators. CSS, ID, LINK
     * @param stringLocator String locator
     * @param nameOfElement Name
     */
    protected BaseElement(String stringLocator, final String nameOfElement) {
        String clearLoc = null;
        if (stringLocator.contains(CSS)) {
            clearLoc = stringLocator.replaceFirst(CSS, "");
            locator = By.cssSelector(clearLoc);
            name = nameOfElement;
        } else if (stringLocator.contains(ID)) {
            clearLoc = stringLocator.replaceFirst(ID, "");
            locator = By.id(clearLoc);
            name = nameOfElement;
        } else if (stringLocator.contains(LINK)) {
            clearLoc = stringLocator.replaceFirst(LINK, "");
            locator = By.linkText(clearLoc);
            name = nameOfElement;
        }  else {
            logger.fatal("UNKNOWN LOCATOR's TYPE. Change to 'By'");
        }
    }

    /**
     * The implementation of an abstract method for logging of BaseEntity
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    protected String formatLogMsg(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", getElementType(), name, Logger.LOG_DELIMITER, message);
    }



    public void waitForElementIsPresent() {
        isPresent(Integer.valueOf(browser.getTimeoutForCondition()));
    }

    public void waitForElementsArePresent() {
        areElementsPresent(Integer.valueOf(browser.getTimeoutForCondition()));
    }

    /**
     * Check for is element present on the page.
     * @return Is element present
     */
    public boolean isPresent() {

        return isPresent(TIMEOUT_WAIT_0);
    }

    /**
     * Check is element present on the page
     * @return is element present
     */
    public boolean isPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), timeout);
        try {
            browser.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(browser.getTimeoutForCondition()), TimeUnit.SECONDS);
            element = browser.getDriver().findElement(locator);
            return element.isDisplayed();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check are elements present on the page
     * @return are elements present
     */
    public boolean areElementsPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(Browser.getInstance().getDriver(), timeout);
        browser.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(browser.getTimeoutForCondition()), TimeUnit.SECONDS);
        try {
            wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
                public Boolean apply(final WebDriver driver) {
                    try {
                        elements = driver.findElements(locator);
                        for (WebElement element : elements) {
                            if (element instanceof WebElement && element.isDisplayed()) {
                                element = (WebElement) element;
                                return element.isDisplayed();
                            }
                        }
                        element = (WebElement) driver.findElement(locator);
                    } catch (Exception e) {
                        return false;
                    }
                    return element.isDisplayed();
                }
            });
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * The method returns the element type (used for logging)
     * @uml.property name="elementType"
     * @return Type of element
     */
    protected abstract String getElementType();

    public void click() {
        waitForElementIsPresent();
        info(getLoc("loc.clicking"));
        if (browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor)browser.getDriver()).executeScript(HIGHLIGHT_BOARDER_JS,element);
        }
        element.click();
    }

    public void clickAndWait() {
        click();
        browser.waitForPageToLoad();
    }

    public String getText() {
        waitForElementIsPresent();
        return element.getText();
    }

    public WebElement getElement() {
        waitForElementIsPresent();
        return element;
    }

    public void setElement (final WebElement elementToSet) {
        element = elementToSet;
    }

    public List<WebElement> getElements() {
        waitForElementsArePresent();
        return elements;
    }
}