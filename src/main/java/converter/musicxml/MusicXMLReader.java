package converter.musicxml;

import converter.music.Measure;
import converter.music.Song;
import converter.music.factories.MeasureFactory;
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
 * MusicXMLReader --- class for reading musicxml files and convert them to the Song class
 */
public class MusicXMLReader {

    private final Properties keys;

    public MusicXMLReader() {
        try (InputStream in = this.getClass().getResourceAsStream("/resources/keys.properties")) {
            keys = new Properties();
            keys.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read keys.properties", e);
        }
    }

    /**
     * read parts from a partwise musicxml file
     * @param path the path to the musicxml file
     * @return a list of Song objects
     */
    public List<Song> readSong(String path) {

        List<Song> songs = new ArrayList<>();
        Element root = getRoot(path);

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
     * @param path the path to the musicxml file
     * @param partNum the index of the part that needs to be read out
     */
    public Song readSongPart(String path, int partNum) {
        Element root = getRoot(path);
        Element part = root.getChildren("part").get(partNum);
        return readSongPart(part, root);
    }

    private Element getRoot(String path) {
        try {
            SAXBuilder builder = new SAXBuilder();
            // ignore deprecated dtd
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            Document musicDoc = builder.build(new File(path));
            return musicDoc.getRootElement();
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("Could not parse the musicxml file", e);
        }
    }

    private Song readSongPart(Element part, Element root) {
        String key = keys.getProperty(part.getChild("measure").getChild("attributes").getChild("key").getChildText("fifths"));
        List<Measure> measures = part.getChildren("measure").stream().map(MeasureFactory::buildMeasure).toList();
        return new Song(getTitle(root), getComposer(root), key, measures);
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
