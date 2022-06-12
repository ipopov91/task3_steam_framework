package steam.test;

import framework.BaseTest;
import framework.LocaleReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
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
        browser.navigate(browser.props.getProperty("URL"));
        logger.step(1);
        String storeNavPullDownItem = LocaleReader.getString("storeNav.categories");
        String storeNavGenrePopupMenuItem = LocaleReader.getString("storeNavCategories.action");

        MainPage mainPage = new MainPage();


        mainPage.switchLanguage(browser.props.getProperty("language"));

        mainPage.navigateStorePullDownMenu(storeNavPullDownItem,storeNavGenrePopupMenuItem);
        logger.step(2);
        CategoryPage categoryPage = new CategoryPage(storeNavGenrePopupMenuItem);


        int maxDiscount = categoryPage.findMaxDiscount();
        categoryPage.selectGameWithMaxDiscount(maxDiscount);

        if (browser.getDriver().getCurrentUrl().contains("agecheck")) {
            AgeVerificationPage ageVerificationPage = new AgeVerificationPage();
            ageVerificationPage.passAgeCheck(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
        }
        logger.step(3);
        GamePage gamePage = new GamePage(String.valueOf(maxDiscount));

        gamePage.navigateInstallSteam();
        logger.step(4);
        InstallPage installPage = new InstallPage();


        installPage.downloadSteam();
        Assert.assertTrue(installPage.isFileDownloaded());

    }
}