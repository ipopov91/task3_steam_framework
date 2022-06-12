package framework;

import framework.elements.Label;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Date;

public abstract class BasePage extends BaseEntity {
    protected By titleLocator;
    protected String title;

    /**
     * @uml.property name="name"
     */
    protected String name; // full name of form that outputted to log, for example, "Form 'Login'"

    /**
     * Contructor
     * @param locator Locator
     * @param formTitle Name
     */
    protected BasePage(final By locator, final String formTitle) {
        init(locator, formTitle);
        assertIsOpen();
    }

    /** Contructor
     * @param locator formlocator
     * @param pageTitle pageTitle
     */
    public BasePage(final String locator, final String pageTitle) {
        long before = new Date().getTime();
        title = pageTitle;
        Label titlePicture = (Label) new Label(locator,title);
        try{
            Assert.assertTrue(titlePicture.isPresent());

            long openTime = new Date().getTime() - before;

            info(String.format(getLoc("loc.page.appears"), title) + String.format(" in %smsec",openTime));

        } catch (Throwable e) {
            fatal(String.format(getLoc("loc.page.doesnt.appears"), title));
        }
    }

    /**
     * For logs
     * @param message Message
     * @return Message
     */
    protected String formatLogMsg(final String message) {
        return message;
    }

    /**
     * Init
     * @param locator Locator
     * @param formTitle Name
     */
    private void init(final By locator, final String formTitle) {
        titleLocator = locator;
        title = formTitle;
        name = String.format(getLoc("loc.page") + " '%1$s'", this.title);
    }

////////
    public void assertIsOpen() {
        long before = new Date().getTime();
        Label pageLabel = new Label(titleLocator);
        try {
            pageLabel.waitForElementIsPresent();
            long openTime = new Date().getTime() - before;

            info(String.format(getLoc("loc.page.appears"), title) + String.format(" in %smsec",openTime));
        } catch (Exception e) {
            fatal(String.format(getLoc("loc.page.doesnt.appears"), title));
        }
    }
}