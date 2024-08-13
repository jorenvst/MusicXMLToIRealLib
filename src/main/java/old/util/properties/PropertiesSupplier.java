package old.util.properties;

import old.musicxml.PartwiseMusicXMLConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * class that manages all the properties files
 */
public class PropertiesSupplier {

    private final Map<PropertiesType, Properties> properties;

    public PropertiesSupplier() {
        try {
            Properties timeProperties = new Properties();
            timeProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("time.properties"));
            Properties chordProperties = new Properties();
            chordProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("chords.properties"));
            Properties keyProperties = new Properties();
            keyProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("keys.properties"));

            properties = new HashMap<>();
            properties.put(PropertiesType.CHORDS, chordProperties);
            properties.put(PropertiesType.KEYS, keyProperties);
            properties.put(PropertiesType.TIME, timeProperties);
        } catch (IOException e) {
            throw new RuntimeException("could not read properties", e);
        }
    }

    public boolean propertyExists(PropertiesType type, String key) {
        return properties.get(type).containsKey(key);
    }

    public String getProperty(PropertiesType type, String key) {
        return properties.get(type).getProperty(key);
    }
}
