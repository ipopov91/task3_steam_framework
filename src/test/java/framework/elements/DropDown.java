package framework.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class DropDown extends BaseElement {
    protected Select select;

    public DropDown(By locator) {
        super(locator);
        select = new Select(this.getElement());
    }

    public void selectDropDownValue(String value) {
        select.selectByValue(value);
    }
}