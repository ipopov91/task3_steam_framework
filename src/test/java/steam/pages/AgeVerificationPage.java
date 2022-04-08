package steam.pages;

import framework.LocaleReader;
import framework.elements.Button;
import framework.elements.DropDown;
import org.openqa.selenium.By;

public class AgeVerificationPage extends SteamBasePage {
    private static String pageLocator = "//a[@class='btnv6_blue_hoverfade btn_medium' ]//span[contains(text(),'%s')]";

    public AgeVerificationPage() {
        super(By.xpath(String.format(pageLocator, LocaleReader.getString("button.viewPage"))));
    }

    public void selectDay(String day) {
        DropDown sltDay = new DropDown(By.id("ageDay"));
        sltDay.selectDropDownValue(day);
    }

    public void selectMonth(String month) {
        DropDown sltMonth = new DropDown(By.id("ageMonth"));
        sltMonth.selectDropDownValue(month);
    }

    public void selectYear(String year) {
        DropDown sltYear = new DropDown(By.id("ageYear"));
        sltYear.selectDropDownValue(year);
    }

    public void passAgeCheck(String day, String month, String year) {
        Button btnViewPage = new Button(By.xpath(String.format(pageLocator, LocaleReader.getString("button.viewPage"))));
        selectDay(day);
        selectMonth(month);
        selectYear(year);
        btnViewPage.clickAndWait();
    }
}