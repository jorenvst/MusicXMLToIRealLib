package core.reader.musicxml;

import music.Harmony;
import music.Measure;
import music.Part;
import music.Time;
import util.PropertiesType;
import util.JDOMUtils;
import util.PropertiesSupplier;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MusicXMLPartReader {

    private String key;
    private int divisions;

    private Time time;

    private final PropertiesSupplier supplier;

    /**
     * reads a part of a partwise MusicXML
     */
    MusicXMLPartReader() {
        key = "C";
        time = new Time(4, 4);
        divisions = 1;

        supplier = new PropertiesSupplier();
    }

    /**
     * reads a part into a Part object from the MusicXML element
     * @param part the element to read from
     * @return a Part object
     */
    Part readPart(Element part) {
        List<Measure> measures = new ArrayList<>();

        for (Element measure : part.getChildren("measure")) {
            setKey(measure);
            setDivision(measure);
            setTime(measure);

            measures.add(createMeasure(measure));
        }

        return new Part(key, divisions, measures);
    }

    /**
     * creates a Measure object from the MusicXML element
     * @param measure the element to read from
     * @return a Measure object
     */
    private Measure createMeasure(Element measure) {
        // set implicit
        boolean implicit = "yes".equals(measure.getAttributeValue("implicit"));

        // set harmony
        Map<Integer, Harmony> harmony = createHarmony(measure);

        return new Measure(time, harmony, implicit);
    }

    /**
     * construct the HashMap containing info about on which beat the chord should land
     * @param measure the element to construct the harmony from
     * @return a HashMap containing the position pointing to the chord
     */
    private Map<Integer, Harmony> createHarmony(Element measure) {
        Map<Integer, Harmony> harmony = new HashMap<>();

        int pos = 0;
        for (Element element : measure.getChildren()) {
            if (element.getName().equals("harmony")) {
                harmony.put(pos / divisions, buildHarmony(element));
            } else if (element.getName().equals("note")) {
                String durationString = JDOMUtils.getChildTextIfExists(element, "duration");
                if (durationString != null) {
                    pos += Integer.parseInt(durationString);
                }
            }
        }

        return harmony;
    }

    /**
     * build a Harmony object from an element
     * @param harmony the element to build from
     * @return a Harmony object
     */
    private Harmony buildHarmony(Element harmony) {
        String root = JDOMUtils.getChildTextIfExists(harmony, "root", "root-step");
        String rootAlter = JDOMUtils.getChildTextIfExists(harmony, "root", "root-alter");
        if (rootAlter != null) {
            root += supplier.getProperty(PropertiesType.CHORDS, rootAlter);
        }
        String kind = supplier.getProperty(PropertiesType.CHORDS, harmony.getChildText("kind"));
        return new Harmony(root, kind);
    }

    /**
     * sets the divisions in which one beat is subdivided, e.g. divisions = 2 in 3/4 means there are 6 subdivisions in the measure
     * this is useful for knowing where the chords should fall
     * @param measure the element to read from
     */
    private void setDivision(Element measure) {
        Element divisionsElement = JDOMUtils.getChildIfExists(measure, "attributes", "divisions");
        if (divisionsElement != null) {
            divisions = Integer.parseInt(divisionsElement.getText());
        }
    }

    /**
     * set the current time signature, this is used to pass to the Measure constructor
     * @param measure element to read from
     */
    private void setTime(Element measure) {
        Element timeElement = JDOMUtils.getChildIfExists(measure, "attributes", "time");
        if (timeElement != null) {
            time = new Time(Integer.parseInt(timeElement.getChildText("beats")), Integer.parseInt(timeElement.getChildText("beat-type")));
        }
    }

    /**
     * set the key of this part
     * @param measure element to read from
     */
    private void setKey(Element measure) {
        String key = JDOMUtils.getChildTextIfExists(measure, "attributes", "key", "fifths");
        if (key != null) {
            this.key = supplier.getProperty(PropertiesType.KEYS, key);
        }
    }
}
