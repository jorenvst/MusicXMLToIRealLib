package core.reader.musicxml;

import core.reader.SongReader;
import music.Composer;
import music.Song;
import util.IgnoreDTDEntityResolver;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.JDOMUtils;

import java.io.File;
import java.io.IOException;


public class MusicXMLReader implements SongReader {

    /**
     * class for reading MusicXML into a Song
     */
    public MusicXMLReader() {

    }

    /**
     * read a file into a Song
     * @param file the file to read
     * @return the Song read from the file
     */
    @Override
    public Song read(File file) {
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            Element root = builder.build(file.getAbsolutePath()).getRootElement();

            return new Song(
                    JDOMUtils.getChildTextIfExists(root, "work", "work-title"),
                    getComposer(root),
                    "unknown",
                    root.getChildren("part").stream().map(part -> new MusicXMLPartReader().readPart(part)).toList()
            );
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("could not open: " + file);
        }
    }

    /**
     * get the composer of the song from the musicxml root element
     * @param root the root element of the musicxml file
     * @return a Composer object containing first name and last name if specified in the file, else the composer is set to "Unknown Composer"
     */
    private Composer getComposer(Element root) {
        Element composer = JDOMUtils.getChildWithAttribute(root, "type", "composer", "identification", "creator");
        if (composer != null) {
            String[] composerText = composer.getText().split(" ");
            return new Composer(composerText[0], composerText[1]);
        }
        return new Composer("Unknown", "Composer");
    }
}
