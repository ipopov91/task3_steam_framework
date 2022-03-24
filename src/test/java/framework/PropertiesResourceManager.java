package framework;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesResourceManager {

    private Properties properties = new Properties();

    /**
     * Constructor
     * @param resourceName Name of resource
     */
    public PropertiesResourceManager(final String resourceName) {
        properties = appendFromResource(properties,resourceName);
    }

    /**
     * Join 2 properties-files
     * @param objProperties Properties
     * @param resourceName Resource Name
     * @return Properties
     */
    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (inputStream != null) {
            try {
                objProperties.load(inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(String.format("Resource %s is not found", resourceName));
        }
        return objProperties;
    }

    /**
     * Receives parameter value by key
     * @param key Key
     * @return Value
     */
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    /**
     * Receives parameter value by key
     * @param key Key
     * @param defaultValue Default Value
     * @return Value
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets the property
     * @param key Key
     * @param value Value
     */
    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }
}