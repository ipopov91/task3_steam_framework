package framework;

import framework.elements.Label;
import org.openqa.selenium.By;

public abstract class BasePage {
    protected static Browser browser = Browser.getInstance();
    protected By pageLocator;
    protected String pageTitle;

    public BasePage(final By locator) {
        init(locator);
        assertIsOpen();
    }

    private void init(final By locator) {
        pageLocator = locator;
    }

    public void assertIsOpen() {
        Label pageLabel = new Label(pageLocator);
        try {
            pageLabel.waitForElementIsPresent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPageTitle() {
        pageTitle = browser.getDriver().getTitle();
        return pageTitle;
    }
}