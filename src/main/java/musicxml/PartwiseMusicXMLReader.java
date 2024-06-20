package musicxml;

import music.Measure;
import music.Time;
import music.factories.MeasureFactory;
import music.Song;
import musicxml.placement.ChordPlacer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * PartwiseMusicXMLReader --- class for reading musicxml files and convert them to the Song class
 */
public class PartwiseMusicXMLReader {

    private final Properties keys;

    public PartwiseMusicXMLReader() {
        try (InputStream in = PartwiseMusicXMLReader.class.getResourceAsStream("/keys.properties")) {
            keys = new Properties();
            keys.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read keys.properties", e);
        }
    }

    /**
     * read parts from a partwise musicxml file
     * @param file the musicxml file
     * @return a list of Song objects
     */
    public List<Song> readSongs(File file) {

        List<Song> songs = new ArrayList<>();
        Element root = getRoot(file);

        for (Element part : root.getChildren("part")) {
            Song song = readSongPart(part, root);
            // only add the song if it has harmony
            if (song.getMeasures().stream().anyMatch(measure -> !measure.getChords().isEmpty())) {
                songs.add(song);
            }
        }
        return songs;
    }

    /**
     * read a part of the song by index
     * @param file the musicxml file
     * @param partNum the index of the part that needs to be read out
     */
    public Song readSongPart(File file, int partNum) {
        Element root = getRoot(file);
        Element part = root.getChildren("part").get(partNum);
        return readSongPart(part, root);
    }

    private Song readSongPart(Element part, Element root) {
        String key = keys.getProperty(part.getChild("measure").getChild("attributes").getChild("key").getChildText("fifths"));
        Time time = null;
        int duration = 1;
        List<Measure> measures = new ArrayList<>();
        for (Element measure : part.getChildren("measure")) {
            if (measure.getChild("attributes") != null && measure.getChild("attributes").getChild("time") != null) {
                time = new Time(
                        Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beats")),
                        Integer.parseInt(measure.getChild("attributes").getChild("time").getChildText("beat-type"))
                );
            }

            if (measure.getChild("attributes") != null && measure.getChild("attributes").getChild("divisions") != null) {
                duration = Integer.parseInt(measure.getChild("attributes").getChildText("divisions"));
            }

            measures.add(MeasureFactory.buildMeasure(measure, time, duration));
        }

        return new Song(getTitle(root), getComposer(root), key, measures);
    }

    /**
     * get root element of musicxml
     */
    private Element getRoot(File file) {
        try {
            SAXBuilder builder = new SAXBuilder();
            // ignore deprecated dtd
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            Document musicDoc = builder.build(file);
            return musicDoc.getRootElement();
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("Could not get or parse the musicxml file", e);
        }
    }

    private String getTitle(Element root) {
        return root.getChild("work").getChildText("work-title");
    }

    private String getComposer(Element root) {
        return root.getChild("identification").getChildText("creator");
    }

    /**
     * IgnoreDTDEntityResolver --- class to ignore deprecated links for dtd
     * e.g. when exporting a musescore file to musicxml, the included dtd is nonexistent and deprecated
     */
    static class IgnoreDTDEntityResolver implements EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId) {
            if (systemId.contains("partwise.dtd")) {
                return new InputSource(new StringReader(""));
            } else {
                return null;
            }
        }
    }
}
