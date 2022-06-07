package framework;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class LocaleReader {
    /**
     * Create Locale Object using the parameters from Configuration.properties
     * Load the Resource Bundle based on the Locale
     * Get the parameter value
     */

    private static String language = new PropertiesResourceManager("Configuration.properties").getProperty("language");
    private static ResourceBundle resource = null;
    private static Locale locale = null;

    public static String getString(String parameter) {
        locale = new Locale(language);
        resource = PropertyResourceBundle.getBundle("localization/Loc", locale);
        String value = resource.getString(parameter);
        //System.out.println(parameter);
        return value;
    }
}