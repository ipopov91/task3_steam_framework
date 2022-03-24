package framework;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public abstract class BaseTest {
    protected static Browser browser = Browser.getInstance();

    @BeforeClass
    public void beforeClass() {
        browser = Browser.getInstance();
    }

    public abstract void runTest();

    @Test
    public void Test() {
        try {
            runTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void after() {
        browser.exit();
    }
}