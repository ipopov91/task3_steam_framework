package framework;
import com.google.common.base.Strings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


public final class Browser {
    private static final String IMPLICIT_WAIT = "implicitlyWait";
    private static final String DEFAULT_PAGE_LOAD_TIMEOUT = "defaultPageLoadTimeout";
    private static final String DEFAULT_CONDITION_TIMEOUT = "defaultConditionTimeout";

    static final String PROPERTIES_FILE = "Configuration.properties";
    private static final String BROWSER_DEFAULT = "chrome";
    private static final String BROWSER_PROP = "browser";

    private static Browser instance;
    private static WebDriver driver;
    public static PropertiesResourceManager props;

    private static String implicitlyWait;
    private static String timeoutForPageLoad;
    private static String timeoutForCondition;

    public static Browsers currentBrowser;

    /**
     * Gets instance of Browser
     * @return browser instance
     */
    public static Browser getInstance() {
        if (instance == null) {
            initProperties();
            try {
                driver = BrowserFactory.setUp(currentBrowser.toString());
                driver.manage().timeouts().implicitlyWait(Long.parseLong(props.getProperty(IMPLICIT_WAIT)), TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = new Browser();
        }
        return instance;
    }

    /**
     * Close browser
     */
    public void exit() {
        try {
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instance = null;
        }
    }

    /**
     * Initialize Properties
     */
    public static void initProperties() {
        props = new PropertiesResourceManager(PROPERTIES_FILE);
        implicitlyWait = props.getProperty(IMPLICIT_WAIT);
        timeoutForPageLoad = props.getProperty(DEFAULT_PAGE_LOAD_TIMEOUT);
        timeoutForCondition = props.getProperty(DEFAULT_CONDITION_TIMEOUT);

        if (Strings.isNullOrEmpty(props.getProperty(BROWSER_PROP))) {
            currentBrowser = Browsers.valueOf(System.getProperty(BROWSER_PROP, BROWSER_DEFAULT).toUpperCase());
        } else {
            currentBrowser = Browsers.valueOf(props.getProperty(BROWSER_PROP).toUpperCase());
        }
    }

    /**
     * Wait for page to load
     */
    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(getTimeoutForPageLoad()));

        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(final WebDriver d) {
                    if (!(d instanceof JavascriptExecutor)) {
                        return true;
                    }
                    Object result = ((JavascriptExecutor) d)
                            .executeScript("return document['readyState'] ? 'complete' == document.readyState : true");
                    if (result != null && result instanceof Boolean && (Boolean) result) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maximize the window
     */
    public void windowMaximize() {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigate to the url
     * @param url url
     */
    public void navigate(final String url) {
        driver.navigate().to(url);
    }

    public String getImplicitlyWait() {
        return implicitlyWait;
    }

    public String getTimeoutForPageLoad() {
        return timeoutForPageLoad;
    }

    public String getTimeoutForCondition() {
        return timeoutForCondition;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public enum Browsers {
        FIREFOX("firefox"),
        CHROME("chrome");

        public String value;

        /**
         * Constructor
         * @param valueToSet value
         */

        Browsers(final String valueToSet) {
            value = valueToSet;
        }

        public String toString() {
            return value;
        }
    }

}