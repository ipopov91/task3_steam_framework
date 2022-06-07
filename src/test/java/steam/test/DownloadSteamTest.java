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
        logger.info("--------==[Open Steam main page]==--------");
        browser.navigate(browser.props.getProperty("URL"));
        logger.step(2);
        logger.info("--------==[Navigate to categories]==--------");
        String storeNavPullDownItem = LocaleReader.getString("storeNav.categories");
        logger.step(3);
        logger.info("--------==[Navigate to action category]==--------");
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
        logger.step(4);
        logger.info("--------==[Navigate to install steam]==--------");
        gamePage.navigateInstallSteam();

        InstallPage installPage = new InstallPage();

        logger.step(5);
        logger.info("--------==[Download Steam]==--------");
        installPage.downloadSteam();
        Assert.assertTrue(installPage.isFileDownloaded());

    }

}