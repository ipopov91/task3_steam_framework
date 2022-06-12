package framework.elements;

import org.openqa.selenium.By;

public class Label extends BaseElement {
    public Label (By locator) {
        super(locator);
    }

    public Label(final By locator, final String name) {
        super(locator, name);
    }

    public Label(String string, String name) {
        super(string, name);
    }

    protected String getElementType() {
        return getLoc("loc.label");
    }
}