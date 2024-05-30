package converter.music.factories;

import converter.music.Measure;
import converter.music.Time;
import converter.musicxml.Repetition;
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
    public static Measure buildMeasure(Element measure) {
        String barLineType = "regular";
        Repetition repetition = Repetition.NONE;
        Time time = null;

        if (measure.getChild("barline") != null) {
            barLineType = measure.getChild("barline").getChildText("bar-style");
            if (measure.getChild("barline").getChild("repeat") != null) {
                repetition = measure.getChild("barline").getChild("repeat").getAttributeValue("direction").equals("forward") ? Repetition.FORWARD : Repetition.BACKWARD;
            }
        }

        if (measure.getChild("attributes") != null && measure.getChild("attributes").getChild("time") != null) {
            time = new Time(
                    Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beats")),
                    Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beat-type"))
            );
        }
        return new Measure(
                measure.getChildren("harmony").stream().map(ChordFactory::buildChord).toList(),
                time,
                barLineType,
                measure.getAttributeValue("implicit") != null && measure.getAttributeValue("implicit").equals("yes"),
                repetition
        );
    }
}
