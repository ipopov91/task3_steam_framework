package framework;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public abstract class BaseTest extends BaseEntity{

    protected static Browser browser = Browser.getInstance();

    @BeforeClass
    public void beforeClass() {
        browser = Browser.getInstance();
    }


    public abstract void runTest();

    @Test
    public void Test() {
        Class<? extends BaseTest> currentClass = this.getClass();

        try {
            logger.logTestName(currentClass.getName());
            runTest();
            logger.logTestEnd(currentClass.getName());
        } catch (Throwable e) {

            logger.warn("");
            logger.warnRed(getLoc("loc.test.failed"));
            logger.warn("");
            throw e;
        }
    }

    /**
     * Format logging
     * @param message Message
     * @return Message
     */
    protected String formatLogMsg(final String message) {
        return message;
    }

    @AfterClass
    public void after() {
        browser.exit();
    }
}