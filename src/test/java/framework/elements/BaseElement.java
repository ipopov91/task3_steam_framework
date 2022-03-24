package framework.elements;
import framework.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseElement {
    private static final String HIGHLIGHT_BOARDER_JS = "arguments[0].style.border='3px solid red'";

    protected static Browser browser = Browser.getInstance();
    protected WebElement element;
    protected List<WebElement> elements;
    protected By locator;

    /**
     * Constructor
     * @param locator
     */
    public BaseElement(final By locator) {
        this.locator = locator;
    }

    /**
     * Get element
     * @return WebElement
     */
    public WebElement getElement() {
        waitForElementIsPresent();
        return element;
    }

    /**
     * Set element
     */
    public void setElement (final WebElement elementToSet) {
        element = elementToSet;
    }

    /**
     * Get elements
     * @return List<WebElement>
     */
    public List<WebElement> getElements() {
        waitForElementsArePresent();
        return elements;
    }

    /**
     * Wait for element is present.
     */
    public void waitForElementIsPresent() {
        isElementPresent(Integer.valueOf(browser.getTimeoutForCondition()));
    }

    /**
     * Wait for elements are present.
     */
    public void waitForElementsArePresent() {
        areElementsPresent(Integer.valueOf(browser.getTimeoutForCondition()));
    }

    /**
     * Check is element present on the page
     * @return is element present
     */
    public boolean isElementPresent(int timeout) {
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
     * Click
     */
    public void click() {
        waitForElementIsPresent();
        if (browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor)browser.getDriver()).executeScript(HIGHLIGHT_BOARDER_JS,element);
        }
        element.click();
    }

    /**
     * Click and wait
     */
    public void clickAndWait() {
        click();
        browser.waitForPageToLoad();
    }

    /**
     * Scroll to element
     */
    public void scrollIntoView() {
        ((JavascriptExecutor)browser.getDriver()).executeScript("arguments[0].scrollIntoView();",element);
    }

    /**
     * Move to element
     */
    public void moveToElement() {
        waitForElementIsPresent();
        Actions action = new Actions(browser.getDriver());
        action.moveToElement(element).perform();
    }

    /**
     * Send keys
     */
    public void sendKeys(Keys key) {
        waitForElementIsPresent();
        browser.getDriver().findElement(locator).sendKeys(key);
    }

    /**
     * Get the element text
     * @return text of element
     */
    public String getText() {
        waitForElementIsPresent();
        return element.getText();
    }
}