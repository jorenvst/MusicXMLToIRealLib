package converter.music;

import java.util.List;

/**
 * Song --- represents a song
 */
public class Song {

    private final String title;
    private final String composer;
    private final String key;
    private final List<Measure> measures;

    /**
     * @param title     title of the song
     * @param composer  composer of the song
     * @param key       key of the song
     * @param measures  a list of all the measures in this song
     */
    public Song(String title, String composer, String key, List<Measure> measures) {
        this.title = title;
        this.composer = composer.trim();
        this.key = key;
        this.measures = measures;
    }

    public String getTitle() {
        return title;
    }

    public String getComposer() {
        return composer;
    }

    public String getComposerFirstName() {
        return composer.substring(0, composer.indexOf(" "));
    }

    public String getComposerLastName() {
        return composer.substring(composer.indexOf(" ") + 1);
    }

    public String getKey() {
        return key;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nComposer: " + composer + "\nKey: " + key + "\n" + measures;
    }
}
