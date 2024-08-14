package util;

public enum PropertiesType {
    CHORDS("chords.properties"),
    KEYS("keys.properties"),
    TIME("time.properties");

    private final String file;

    PropertiesType(String file) {
        this.file = file;
    }

    public String file() {
        return file;
    }
}
