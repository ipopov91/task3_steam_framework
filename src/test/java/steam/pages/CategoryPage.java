package steam.pages;

import framework.elements.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CategoryPage extends SteamBasePage {
    private static String pageLocator = "//h2[@class='pageheader' and contains(text(),'%s')]";

    private static String recommendedSpecialsDiscountBaseLocator = "//div[@id='specials_container']//div[@class='discount_pct']";
    private static String recommendedSpecialsDiscountLocator = "//div[@id='specials_container']//div[@class='discount_pct' and contains(text(),'%s')]";

    Label lblDiscounts = new Label(By.xpath(recommendedSpecialsDiscountBaseLocator));

    public CategoryPage(String pageLocatorValue) {
        super(By.xpath(String.format(pageLocator, pageLocatorValue)));
    }

    public int findMaxDiscount() {
        List<WebElement> discounts = lblDiscounts.getElements();
        int maxGameDiscount = Integer.parseInt(discounts.get(0).getText().replaceAll("[-%]",""));
        for (WebElement discount : discounts) {
            Integer gameDiscount = Integer.parseInt(discount.getText().replaceAll("[-%]",""));
            if( gameDiscount > maxGameDiscount) {
                maxGameDiscount = gameDiscount;
            }
        }
        return maxGameDiscount;
    }

    public void selectGameWithMaxDiscount (int maxDiscount) {
        Label lblGameMaxDiscount = new Label(By.xpath(String.format(recommendedSpecialsDiscountLocator,maxDiscount)));
        lblGameMaxDiscount.clickAndWait();
    }
}