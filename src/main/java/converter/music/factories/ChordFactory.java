package converter.music.factories;

import converter.music.Chord;
import org.jdom2.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ChordFactory --- Factory for building chords
 */
public class ChordFactory {

    /**
     * build a chord from musicxml
     * @param chord the element that needs to be converted into a Chord
     * @return a new Chord
     */
    public static Chord buildChord(Element chord) {

        String root = chord.getChild("root").getChildText("root-step");
        String bass = null;
        List<String> alterations = new ArrayList<>();

        try (InputStream in2 = ChordFactory.class.getResourceAsStream("/resources/chords.properties")) {
            Properties chords = new Properties();
            chords.load(in2);

            if (chord.getChild("root").getChild("root-alter") != null) {
                root += chords.getProperty(chord.getChild("root").getChildText("root-alter"));
            }

            if (chord.getChild("bass") != null) {
                bass = chord.getChild("bass").getChildText("bass-step");
                if (chord.getChild("bass").getChild("bass-alter") != null) {
                    bass += chords.getProperty(chord.getChild("bass").getChildText("bass-alter"));
                }
            }

            if (chord.getChild("degree") != null) {
                for (Element degree : chord.getChildren("degree")) {
                    alterations.add(chords.getProperty(degree.getChildText("degree-alter")) + degree.getChildText("degree-value"));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load chords.properties", e);
        }

        return new Chord(root, chord.getChildText("kind"), alterations, bass);
    }
}
