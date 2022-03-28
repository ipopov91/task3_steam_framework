package framework;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesResourceManager {

    private Properties properties = new Properties();

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

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }
}