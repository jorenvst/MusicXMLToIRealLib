package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesSupplier {

    private final Map<PropertiesType, Properties> propertiesMap;

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

    public boolean propertyExists(PropertiesType type, String key) {
        return propertiesMap.get(type).containsKey(key);
    }

    public String getProperty(PropertiesType type, String key) {
        return propertiesMap.get(type).getProperty(key);
    }
}
