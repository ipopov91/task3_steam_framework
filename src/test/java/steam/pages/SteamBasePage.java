package steam.pages;

import framework.BasePage;
import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SteamBasePage extends BasePage {
    private static String pageLocator = "logo";

    private static String languageDropdownLocator = "language_pulldown";
    private static String baseSingleLanguageLocator = "//div[@id='language_dropdown']//a";
    private static String singleLanguageLocator = "//div[@id='language_dropdown']//a[contains(text(),'%s')]";
    private static String installSteamLocator = "header_installsteam_btn_content";
    private static String storeNavPullDownItemLocator = "//div[@class='store_nav']//a[@class='pulldown_desktop' and contains(text(), '%s')]";
    private static String storeNavGenrePopupMenuItemLocator = "//div[@class='popup_menu_subheader popup_genre_expand_header responsive_hidden']//a[@class='popup_menu_item' and contains(text(),'%s')]";

    public SteamBasePage() {
        super(By.className(pageLocator), "Basepage");
    }

    public SteamBasePage(final By locator, String name) {
        super(locator, name);
    }

    /**
     * Navigate to install steam
     */
    public void navigateInstallSteam() {
        Button btnInstallSteam = new Button(By.className(installSteamLocator), "install steam");
        btnInstallSteam.click();
    }

    /**
     * Switch language
     * @param langToSet
     */
    public void switchLanguage(String langToSet) {
        Button btnLanguage = new Button(By.id(languageDropdownLocator), "switch language");
        btnLanguage.click();
        chooseLanguage(langToSet);
    }

    /**
     * Select language
     * @param lang
     */
    public void chooseLanguage(String lang) {
        if (isLanguagePresent(lang)) {
            Label labelSingleLanguage = new Label(By.xpath(String.format(singleLanguageLocator, lang)), "select language");
            labelSingleLanguage.click();
        }
    }

    /**
     * Check language is present
     * @param lang
     * @return Boolean
     */
    public boolean isLanguagePresent(String lang) {
        Label labelLanguages = new Label(By.xpath(baseSingleLanguageLocator), "language");
        List<WebElement> languages = labelLanguages.getElements();
        for (WebElement language : languages) {
            if(language.getText().contains(lang)){
                return true;
            };
        }
        return false;
    }

    /**
     * Navigate Store PullDown Menu
     */
    public void navigateStorePullDownMenu (String storeNavPullDownItemToSelect, String storeNavGenrePopupMenuItemToSelect) {
        Label lblStoreNavItem = new Label(By.xpath(String.format(storeNavPullDownItemLocator,storeNavPullDownItemToSelect)),
                "store navigation");
        Label labelStoreNavPopupMenuItem = new Label(By.xpath(String.format(storeNavGenrePopupMenuItemLocator,storeNavGenrePopupMenuItemToSelect)),
                "store navigation genre popup menu");
        lblStoreNavItem.click();
        labelStoreNavPopupMenuItem.clickAndWait();
    }
}