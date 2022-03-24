package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

import framework.Browser.Browsers;

public abstract class BrowserFactory {

    private final static String CHROME_DRIVER_PATH = "src/test/resources/drivers/chromedriver.exe";
    private final static String FIREFOX_DRIVER_PATH = "src/test/resources/drivers/geckodriver.exe";

    /**
     * Setting up Driver
     * @param type Browser type
     * @return WebDriver
     */
    public static WebDriver setUp(final Browsers type) {

        WebDriver driver = null;

        switch(type) {
            case CHROME:
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.prompt_for_download", false);
                prefs.put("safebrowsing.enabled", true);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("--safebrowsing-disable-download-protection");
                chromeOptions.addArguments("safebrowsing-disable-extension-blacklist");

                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                driver = new ChromeDriver(chromeOptions);

                break;

            case FIREFOX:
                FirefoxProfile profile = new FirefoxProfile();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                profile.setPreference("browser.downLoad.folderList", 1);
                profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                profile.setPreference("browser.download.manager.closeWhenDone", true);
                profile.setPreference("browser.download.manager.focusWhenStarting", false);
                profile.setPreference("browser.download.manager.showWhenStarting",false);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/octet-stream");
                firefoxOptions.setProfile(profile);

                System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                break;
        }
        return driver;
    }

    /**
     * Setting up Driver
     * @param type Browser type
     * @return WebDriver
     * @throws NamingException NamingException
     */
    public static WebDriver setUp(final String type) throws NamingException {
        for (Browsers t : Browsers.values()) {
            if (t.toString().equalsIgnoreCase(type)) {
                return setUp(t);
            }
        }
        throw new NamingException("Unexpected value, browser is not supported: " + type);
    }
}