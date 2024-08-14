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

    MusicXMLPartReader() {
        key = "C";
        time = new Time(4, 4);
        divisions = 1;

        supplier = new PropertiesSupplier();
    }

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

    private Measure createMeasure(Element measure) {
        // set implicit
        boolean implicit = "yes".equals(measure.getAttributeValue("implicit"));

        // set harmony
        Map<Integer, Harmony> harmony = createHarmony(measure);

        return new Measure(time, harmony, implicit);
    }

    private Map<Integer, Harmony> createHarmony(Element measure) {
        Map<Integer, Harmony> harmony = new HashMap<>();

        int pos = 0;
        for (Element element : measure.getChildren()) {
            if (element.getName().equals("harmony")) {
                harmony.put(pos / divisions, buildHarmony(element));
            } else if (element.getName().equals("note")) {
                String durationString = old.util.JDOMUtils.getChildTextIfExists(element, "duration");
                if (durationString != null) {
                    pos += Integer.parseInt(durationString);
                }
            }
        }

        return harmony;
    }

    private Harmony buildHarmony(Element harmony) {
        String root = old.util.JDOMUtils.getChildTextIfExists(harmony, "root", "root-step");
        String rootAlter = old.util.JDOMUtils.getChildTextIfExists(harmony, "root", "root-alter");
        if (rootAlter != null) {
            root += supplier.getProperty(PropertiesType.CHORDS, rootAlter);
        }
        String kind = supplier.getProperty(PropertiesType.CHORDS, harmony.getChildText("kind"));
        return new Harmony(root, kind);
    }

    private void setDivision(Element measure) {
        Element divisionsElement = JDOMUtils.getChildIfExists(measure, "attributes", "divisions");
        if (divisionsElement != null) {
            divisions = Integer.parseInt(divisionsElement.getText());
        }
    }

    private void setTime(Element measure) {
        Element timeElement = JDOMUtils.getChildIfExists(measure, "attributes", "time");
        if (timeElement != null) {
            time = new Time(Integer.parseInt(timeElement.getChildText("beats")), Integer.parseInt(timeElement.getChildText("beat-type")));
        }
    }

    private void setKey(Element measure) {
        String key = JDOMUtils.getChildTextIfExists(measure, "attributes", "key", "fifths");
        if (key != null) {
            this.key = supplier.getProperty(PropertiesType.KEYS, key);
        }
    }
}
