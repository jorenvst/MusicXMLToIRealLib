package music.factories;

import music.Measure;
import musicxml.Repetition;
import music.Time;
import org.jdom2.Element;

/**
 * MeasureFactory --- Factory for building Measures
 */
public class MeasureFactory {

    /**
     * build a measure from musicxml
     * @param measure the element that needs to be converted into a Measure
     * @return a new Measure
     */
    public static Measure buildMeasure(Element measure, Time time) {
        String barLineType = "regular";
        Repetition repetition = Repetition.NONE;

        if (measure.getChild("barline") != null) {
            barLineType = measure.getChild("barline").getChildText("bar-style");
            if (measure.getChild("barline").getChild("repeat") != null) {
                repetition = measure.getChild("barline").getChild("repeat").getAttributeValue("direction").equals("forward") ? Repetition.FORWARD : Repetition.BACKWARD;
            }
        }

        return new Measure(
                measure.getChildren("harmony").stream().map(ChordFactory::buildChord).toList(),
                measure.getChildren("note").stream().map(NoteFactory::buildNote).toList(),
                time,
                barLineType,
                measure.getAttributeValue("implicit") != null && measure.getAttributeValue("implicit").equals("yes"),
                repetition
        );
    }
}
