package steam.pages;

import org.openqa.selenium.By;

public class MainPage extends SteamBasePage {
    private static String pageLocator = "//div[@class='home_page_sign_in_ctn small']";

    public MainPage() {
        super(By.xpath(pageLocator), "Home Page");
    }
}