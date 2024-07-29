package util;

import musicxml.PartwiseMusicXMLReader;

import java.io.IOException;
import java.util.Properties;

public class PropertiesSupplier {

    private final Properties timeProperties = new Properties();
    private final Properties chordProperties = new Properties();

    public PropertiesSupplier() {
        try {
            timeProperties.load(PartwiseMusicXMLReader.class.getResourceAsStream("time.properties"));
            chordProperties.load(PartwiseMusicXMLReader.class.getResourceAsStream("chords.properties"));
        } catch (IOException e) {
            throw new RuntimeException("could not read properties", e);
        }
    }

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
}
