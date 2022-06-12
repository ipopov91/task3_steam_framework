package framework.elements;

import org.openqa.selenium.By;

public class Button extends BaseElement {
    public Button (By locator) {
        super(locator);
    }

    /**
     *
     * @param locator
     * @param name
     */
    public Button(final By locator, final String name) {
        super(locator, name);
    }

    protected String getElementType() {
        return getLoc("loc.button");
    }

}