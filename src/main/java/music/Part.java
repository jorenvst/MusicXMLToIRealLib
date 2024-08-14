package music;

import java.util.ArrayList;
import java.util.List;

public record Part(String key, int divisions, List<Measure> measures) {

    /**
     * represents a Part of a song
     * @param key the key in which this song is, e.g. Ab
     * @param divisions the number of subdivisions a beat of a measure should be split, used for determining where notes and chords land
     * @param measures a list containing all the measures of the part
     */
    public Part(String key, int divisions, List<Measure> measures) {
        this.key = key;
        this.divisions = divisions;
        this.measures = new ArrayList<>(measures);
    }

}
