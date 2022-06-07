package steam.test;

import framework.BaseTest;
import framework.LocaleReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import steam.pages.*;
import java.io.File;


@Owner("Igor Popov")
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

    @Step("Steam download test")
    public void runTest() {
        logger.step(1);
        logger.info("Logging into https://store.steampowered.com\n");

        browser.navigate(browser.props.getProperty("URL"));

        String storeNavPullDownItem = LocaleReader.getString("storeNav.categories");
        String storeNavGenrePopupMenuItem = LocaleReader.getString("storeNavCategories.action");

        MainPage mainPage = new MainPage();

        logger.step(2);
        logger.info("Click the \"language\" drop-down button and select \"English\" from the list.\n");
        mainPage.switchLanguage(browser.props.getProperty("language"));

        logger.step(3);
        logger.info("Click the \"Categories\" drop-down menu and click the \"Actions\" label \n");
        mainPage.navigateStorePullDownMenu(storeNavPullDownItem,storeNavGenrePopupMenuItem);

        CategoryPage categoryPage = new CategoryPage(storeNavGenrePopupMenuItem);

        logger.step(4);
        logger.info("Choose the game with the maximum discount\n");
        int maxDiscount = categoryPage.findMaxDiscount();
        categoryPage.selectGameWithMaxDiscount(maxDiscount);

        if (browser.getDriver().getCurrentUrl().contains("agecheck")) {
            logger.info("Age verification\n");
            AgeVerificationPage ageVerificationPage = new AgeVerificationPage();
            ageVerificationPage.passAgeCheck(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
        }

        GamePage gamePage = new GamePage(String.valueOf(maxDiscount));
        logger.step(4);
        logger.info("Click on the \"Install Steam\" button\n");
        gamePage.navigateInstallSteam();

        InstallPage installPage = new InstallPage();

        logger.step(5);
        logger.info("Download Steam!");
        installPage.downloadSteam();
        Assert.assertTrue(installPage.isFileDownloaded());

    }
}