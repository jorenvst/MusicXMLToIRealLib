package util;

import musicxml.PartwiseMusicXMLConverter;

import java.io.IOException;
import java.util.Properties;

public class PropertiesSupplier {

    private final Properties timeProperties = new Properties();
    private final Properties chordProperties = new Properties();
    private final Properties keyProperties = new Properties();

    public PropertiesSupplier() {
        try {
            timeProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("time.properties"));
            chordProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("chords.properties"));
            keyProperties.load(PartwiseMusicXMLConverter.class.getResourceAsStream("keys.properties"));
        } catch (IOException e) {
            throw new RuntimeException("could not read properties", e);
        }
    }

    // TODO: use enum to select properties file
    public boolean timePropertyExists(String key) {
        return timeProperties.containsKey(key);
    }

    public boolean ChordPropertyExists(String key) {
        return chordProperties.containsKey(key);
    }

    public String getTimeProperty(String key) {
        return timeProperties.getProperty(key);
    }

    public String getChordProperty(String key) {
        return chordProperties.getProperty(key);
    }

    public String getKeyProperty(String key) {
        return keyProperties.getProperty(key);
    }
}
