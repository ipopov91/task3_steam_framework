package steam.test;

import framework.BaseTest;
import framework.LocaleReader;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import steam.pages.*;
import java.io.File;

public class DownloadSteamTest extends BaseTest {
    private final String DEFAULT_DAY = "23";
    private final String DEFAULT_MONTH = "February";
    private final String DEFAULT_YEAR = "1991";

    @BeforeTest
    public void beforeTest() {
        String filePath = System.getProperty("user.home") + browser.props.getProperty("downloadDirectory")
                + browser.props.getProperty("installSteamFileName");
        File downloadFile = new File(filePath);
        if(downloadFile.exists()) {
            downloadFile.delete();
        }
    }

    public void runTest() {
        browser.navigate(browser.props.getProperty("URL"));

        String storeNavPullDownItem = LocaleReader.getString("storeNav.categories");
        String storeNavGenrePopupMenuItem = LocaleReader.getString("storeNavCategories.action");

        MainPage mainPage = new MainPage();
        mainPage.switchLanguage(browser.props.getProperty("language"));
        mainPage.navigateStorePullDownMenu(storeNavPullDownItem,storeNavGenrePopupMenuItem);

        CategoryPage categoryPage = new CategoryPage(storeNavGenrePopupMenuItem);
        int maxDiscount = categoryPage.findMaxDiscount();
        categoryPage.selectGameWithMaxDiscount(maxDiscount);

        if (browser.getDriver().getCurrentUrl().contains("agecheck")) {
            AgeVerificationPage ageVerificationPage = new AgeVerificationPage();
            ageVerificationPage.passAgeCheck(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
        }

        GamePage gamePage = new GamePage(String.valueOf(maxDiscount));
        gamePage.navigateInstallSteam();

        InstallPage installPage = new InstallPage();
        installPage.downloadSteam();
        Assert.assertTrue(installPage.isFileDownloaded());
    }
}