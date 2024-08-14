package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesSupplier {

    private final Map<PropertiesType, Properties> propertiesMap;

    /**
     * class for managing different properties files
     */
    public PropertiesSupplier() {
        try {
            propertiesMap = new HashMap<>();
            for (PropertiesType type : PropertiesType.values()) {
                Properties properties = new Properties();
                properties.load(PropertiesSupplier.class.getResourceAsStream(type.file()));
                propertiesMap.put(type, properties);
            }
        } catch (IOException e) {
            throw new RuntimeException("could not read properties", e);
        }
    }

    /**
     * check if a property exists in a given properties file determined by PropertiesType
     * @param type the properties type to search in
     * @param key the key of the property that should be searched
     * @return true if the key is present in the properties file, false if not
     */
    public boolean propertyExists(PropertiesType type, String key) {
        return propertiesMap.get(type).containsKey(key);
    }

    /**
     * get a property from a properties file determined by PropertiesType
     * @param type the properties type to search in
     * @param key the key of the property to get
     * @return the value associated with the key if it exists, else null
     */
    public String getProperty(PropertiesType type, String key) {
        return propertiesMap.get(type).getProperty(key);
    }
}
