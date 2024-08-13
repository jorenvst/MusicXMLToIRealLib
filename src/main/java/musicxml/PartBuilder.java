package musicxml;

import music.*;
import org.jdom2.Element;
import util.JDOMUtils;
import util.PropertiesSupplier;

import java.util.List;

public class PartBuilder {

    private final Element musicXMLPart;
    private final PropertiesSupplier supplier;

    private int divisions = 1;
    private int offset = 0;
    private Time time = Time.T44;

    public PartBuilder(Element musicXMLPart) {
        this.musicXMLPart = musicXMLPart;
        this.supplier = new PropertiesSupplier();
    }

    public Part build() {
        // this only supports keys written using fifths
        // more info at: https://www.w3.org/2021/06/musicxml40/musicxml-reference/elements/key/
        Part part = new Part().setKey(supplier.getKeyProperty(JDOMUtils.getChildTextIfExists(musicXMLPart, "measure", "attributes", "key", "fifths")));

        for (Element measureElement : musicXMLPart.getChildren("measure")) {
            setDivision(measureElement);
            setTime(measureElement);

            String implicitString = measureElement.getAttributeValue("implicit");
            List<Element> barLines = measureElement.getChildren("barline");

            // TODO: refactor
            BarLine startBarLine;
            BarLine endBarLine;
            if (!barLines.stream().filter(b -> b.getAttributeValue("location").equals("left")).toList().isEmpty()) {
                startBarLine = BarLine.valueOf(barLines.stream().filter(b -> b.getAttributeValue("location").equals("left")).toList().getFirst()
                        .getChildText("bar-style").toUpperCase().replace("-", "_"));
            } else {
                startBarLine = BarLine.REGULAR;
            }
            if (!barLines.stream().filter(b -> b.getAttributeValue("location").equals("right")).toList().isEmpty()) {
                endBarLine = BarLine.valueOf(barLines.stream().filter(b -> b.getAttributeValue("location").equals("right")).toList().getFirst()
                        .getChildText("bar-style").toUpperCase().replace("-", "_"));
            } else {
                endBarLine = BarLine.REGULAR;
            }
            Measure measure = new Measure(
                    time,
                    divisions,
                    implicitString != null && implicitString.equals("yes"),
                    startBarLine,
                    endBarLine
            );

            offset = 0;
            for (Element e : measureElement.getChildren()) {
                if (e.getName().equals("harmony")) {
                    measure.addHarmony(getHarmony(e));
                }
                if (e.getName().equals("note")) {
                    String durationString = JDOMUtils.getChildTextIfExists(e, "duration");
                    if (durationString != null) offset += Integer.parseInt(durationString);
                }
            }
            part.addMeasure(measure);
        }
        return part;
    }

    private void setDivision(Element measureElement) {
        Element divisionsElement = JDOMUtils.getChildIfExists(measureElement, "attributes", "divisions");
        if (divisionsElement != null) {
            divisions = Integer.parseInt(divisionsElement.getText());
        }
    }

    private void setTime(Element measureElement) {
        Element timeElement = JDOMUtils.getChildIfExists(measureElement, "attributes", "time");
        if (timeElement != null) {
            String timeString = timeElement.getChildText("beats") + "/" + timeElement.getChildText("beat-type");
            if (supplier.timePropertyExists(timeString)) {
                time = Time.valueOf(supplier.getTimeProperty(timeString));
            }
        }
    }

    private Harmony getHarmony(Element harmonyElement) {
        String root = JDOMUtils.getChildTextIfExists(harmonyElement, "root", "root-step");
        String rootAlter = JDOMUtils.getChildTextIfExists(harmonyElement, "root", "root-alter");
        if (rootAlter != null) {
            root += supplier.getChordProperty(rootAlter);
        }

        String kind = supplier.getChordProperty(harmonyElement.getChildText("kind"));

        String offsetString = JDOMUtils.getChildTextIfExists(harmonyElement, "offset");
        if (offsetString != null) {
            offset = Integer.parseInt(offsetString);
        }

        return new Harmony(root, kind, offset);
    }
}
