package musicxml;

import music.Harmony;
import music.Measure;
import music.Part;
import music.Time;
import org.jdom2.Element;
import util.JdomUtils;
import util.PropertiesSupplier;

public class PartBuilder {

    private final Element musicXmlPart;
    private final PropertiesSupplier supplier;

    private int divisions = 1;
    private int offset = 0;
    private Time time = Time.T44;

    public PartBuilder(Element musicXmlPart) {
        this.musicXmlPart = musicXmlPart;
        this.supplier = new PropertiesSupplier();
    }

    public Part build() {
        Part part = new Part();
        for (Element measureElement : musicXmlPart.getChildren("measure")) {
            setDivision(measureElement);
            setTime(measureElement);

            Measure measure = new Measure(time, divisions);
            offset = 0;
            for (Element e : measureElement.getChildren()) {
                if (e.getName().equals("harmony")) {
                    measure.addHarmony(getHarmony(e));
                }
                if (e.getName().equals("note")) {
                    String durationString = JdomUtils.getChildTextIfExists(e, "duration");
                    if (durationString != null) offset += Integer.parseInt(durationString);
                }
            }
            part.addMeasure(measure);
        }
        return part;
    }

    private void setDivision(Element measureElement) {
        Element divisionsElement = JdomUtils.getChildIfExists(measureElement, "attributes", "divisions");
        if (divisionsElement != null) {
            divisions = Integer.parseInt(divisionsElement.getText());
        }
    }

    private void setTime(Element measureElement) {
        Element timeElement = JdomUtils.getChildIfExists(measureElement, "attributes", "time");
        if (timeElement != null) {
            String timeString = timeElement.getChildText("beats") + "/" + timeElement.getChildText("beat-type");
            if (supplier.timePropertyExists(timeString)) {
                time = Time.valueOf(supplier.getTimeProperty(timeString));
            }
        }
    }

    private Harmony getHarmony(Element harmonyElement) {
        String root = JdomUtils.getChildTextIfExists(harmonyElement, "root", "root-step");
        String rootAlter = JdomUtils.getChildTextIfExists(harmonyElement, "root", "root-alter");
        if (rootAlter != null) {
            root += supplier.getChordProperty(rootAlter);
        }

        String kind = supplier.getChordProperty(harmonyElement.getChildText("kind"));

        String offsetString = JdomUtils.getChildTextIfExists(harmonyElement, "offset");
        if (offsetString != null) {
            offset = Integer.parseInt(offsetString);
        }

        return new Harmony(root, kind, offset);
    }
}
