package steam.pages;

import framework.LocaleReader;
import framework.elements.Button;
import framework.elements.DropDown;
import org.openqa.selenium.By;

public class AgeVerificationPage extends SteamBasePage {
    private static String pageLocator = "//a[@class='btnv6_blue_hoverfade btn_medium' ]//span[contains(text(),'%s')]";

    private DropDown sltDay = new DropDown(By.id("ageDay"), "select day");
    private DropDown sltMonth = new DropDown(By.id("ageMonth"), "select month");
    private DropDown sltYear = new DropDown(By.id("ageYear"), "select year");

    public AgeVerificationPage() {
        super(By.xpath(String.format(pageLocator, LocaleReader.getString("button.viewPage"))), "Age Verification page");
    }

    public void selectDate(String day, String month, String year) {
        sltDay.selectDropDownValue(day);
        sltMonth.selectDropDownValue(month);
        sltYear.selectDropDownValue(year);
    }

    public void passAgeCheck(String day, String month, String year) {
        Button btnViewPage = new Button(By.xpath(String.format(pageLocator, LocaleReader.getString("button.viewPage"))), "view page");
        selectDate(day, month, year);
        btnViewPage.clickAndWait();
    }
}