package steam.pages;

import framework.LocaleReader;
import framework.elements.Button;
import framework.elements.DropDown;
import org.openqa.selenium.By;

public class AgeVerificationPage extends SteamBasePage {
    private static String pageLocator = "//a[@class='btnv6_blue_hoverfade btn_medium' ]//span[contains(text(),'%s')]";
    private static By viewPageButtonLocatorBy = By.xpath(String.format(pageLocator, LocaleReader.getString("button.viewPage")));

    private static By selectDayLocatorBy = By.id("ageDay");
    private static By selectMonthLocatorBy = By.id("ageMonth");
    private static By selectYearLocatorBy = By.id("ageYear");

    private DropDown sltDay = new DropDown(selectDayLocatorBy);
    private DropDown sltMonth = new DropDown(selectMonthLocatorBy);
    private DropDown sltYear = new DropDown(selectYearLocatorBy);

    private Button btnViewPage = new Button(viewPageButtonLocatorBy);

    public AgeVerificationPage() {
        super(viewPageButtonLocatorBy);
    }

    public void selectDay(String day) {
        sltDay.selectDropDownValue(day);
    }

    public void selectMonth(String month) {
        sltMonth.selectDropDownValue(month);
    }

    public void selectYear(String year) {
        sltYear.selectDropDownValue(year);
    }

    public void passAgeCheck(String day, String month, String year) {
        selectDay(day);
        selectMonth(month);
        selectYear(year);
        btnViewPage.clickAndWait();
    }
}