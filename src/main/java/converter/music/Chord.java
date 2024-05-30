package converter.music;

import java.util.ArrayList;
import java.util.List;

/**
 * Chord --- represents a chord in music
 * created when musicxml file is read
 * @param root        required, the root note of the chord
 * @param kind        required, the quality of the chord, e.g. maj7
 * @param alterations optional, alterations or extensions to the chord e.g. #11, b5
 * @param bass        optional, the inversion of the chord or over what bass note the chord should be played
 */
public record Chord(String root, String kind, List<String> alterations, String bass) {

    /**
     * check if a bass note is specified
     */
    public boolean hasBass() {
        return bass != null;
    }

    public List<String> alterations() {
        return new ArrayList<>(alterations);
    }

    @Override
    public String toString() {
        if (bass == null) {
            return root + " " + kind;
        } else {
            return root + " " + kind + "/" + bass;
        }
    }
}
