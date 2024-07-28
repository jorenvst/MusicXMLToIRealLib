package musicxml;

import music.Harmony;
import music.Measure;
import music.Part;
import music.Time;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class PartwiseMusicXMLReader {

    private final Properties timeProperties = new Properties();
    private final Properties chordProperties = new Properties();

    public PartwiseMusicXMLReader() {
        try {
            timeProperties.load(PartwiseMusicXMLReader.class.getResourceAsStream("time.properties"));
            chordProperties.load(PartwiseMusicXMLReader.class.getResourceAsStream("chords.properties"));
        } catch (IOException e) {
            throw new RuntimeException("could not read properties", e);
        }
    }

    public Collection<Part> readParts(String path) {
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            return builder.build(path).getRootElement().getChildren("part").stream()
                    .map(this::readPart).collect(Collectors.toSet());
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("could not read " + path, e);
        }
    }

    private Part readPart(Element musicXmlPart) {
        int divisions = 1;
        Time time = Time.T44;
        Part part = new Part();
        for (Element measureElement : musicXmlPart.getChildren("measure")) {
            Element divisionsElement = getChild(measureElement, "attributes", "divisions");
            if (divisionsElement != null) {
                divisions = Integer.parseInt(divisionsElement.getText());
            }

            Element timeElement = getChild(measureElement, "attributes", "time");
            if (timeElement != null) {
                String timeString = timeElement.getChildText("beats") + "/" + timeElement.getChildText("beat-type");
                if (timeProperties.containsKey(timeString)) {
                    time = Time.valueOf(timeProperties.getProperty(timeString));
                }
            }

            Measure measure = new Measure(time);
            for (Element harmony : measureElement.getChildren("harmony")) {
                String root = getChild(harmony, "root", "root-step").getText();
                Element rootAlter = getChild(harmony, "root", "root-alter");
                if (rootAlter != null) {
                    root += chordProperties.getProperty(rootAlter.getText());
                }

                String kind = chordProperties.getProperty(harmony.getChildText("kind"));

                measure.addHarmony(new Harmony(root, kind));
            }
            part.addMeasure(measure);
        }
        return part;
    }

    private Element getChild(Element element, String... children) {
        return getChild(element, 0, children);
    }

    private Element getChild(Element element, int index, String... children) {
        if (element == null) return null;
        if (children.length == index) return element;
        return getChild(element.getChild(children[index]), index + 1, children);
    }
}
