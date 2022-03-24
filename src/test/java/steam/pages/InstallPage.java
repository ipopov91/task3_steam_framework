package steam.pages;

import framework.BasePage;
import framework.Browser;
import framework.LocaleReader;
import framework.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;

public class InstallPage extends BasePage {
    private static final String installSteamButtonLocator = "//div[@id='about_greeting']//a[contains(text(),'%s')]";
    private static final By installSteamButtonLocatorBy = By.xpath(String.format(installSteamButtonLocator, LocaleReader.getString("button.installSteam")));

    Button btnInstallSteam = new Button(installSteamButtonLocatorBy);

    public InstallPage() {
        super(installSteamButtonLocatorBy);
    }

    /**
     * Click to download steam
     */
    public void downloadSteam() {
        btnInstallSteam.click();
    }

    /**
     * Check file is downloaded
     * @return Boolean
     */
    public boolean isFileDownloaded() {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), Long.parseLong(browser.getTimeoutForCondition()));
        String filePath = System.getProperty("user.home") + Browser.props.getProperty("downloadDirectory")
                + Browser.props.getProperty("installSteamFileName");
        File file = new File(filePath);
        try {
            wait.until((ExpectedCondition<Boolean>) webDriver -> file.exists());
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}