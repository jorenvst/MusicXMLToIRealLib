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

    private Composer getComposer(Element root) {
        Element composer = JDOMUtils.getChildWithAttribute(root, "type", "composer", "identification", "creator");
        if (composer != null) {
            String[] composerText = composer.getText().split(" ");
            return new Composer(composerText[0], composerText[1]);
        }
        return new Composer("Unknown", "Composer");
    }
}
