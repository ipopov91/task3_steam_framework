package framework;

import framework.elements.Label;
import org.openqa.selenium.By;

public abstract class BasePage {
    protected static Browser browser = Browser.getInstance();
    protected By pageLocator;
    protected String pageTitle;

    /**
     * Contructor
     * @param locator Locator
     */
    public BasePage(final By locator) {
        init(locator);
        assertIsOpen();
    }

    /**
     * Initialization
     * @param locator Locator
     */
    private void init(final By locator) {
        pageLocator = locator;
    }

    /**
     * Verify the page opens
     */
    public void assertIsOpen() {
        Label pageLabel = new Label(pageLocator);
        try {
            pageLabel.waitForElementIsPresent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get page title
     * @return PageTitle
     */
    public String getPageTitle() {
        pageTitle = browser.getDriver().getTitle();
        return pageTitle;
    }
}