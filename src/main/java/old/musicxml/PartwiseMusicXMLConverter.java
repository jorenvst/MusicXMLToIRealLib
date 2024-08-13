package old.musicxml;

import old.irealpro.Chart;
import old.music.Composer;
import old.music.Song;
import old.util.IgnoreDTDEntityResolver;
import old.util.JDOMUtils;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.Collection;

public class PartwiseMusicXMLConverter {

    private Song readSong(String path) {
        try {
            SAXBuilder builder = new SAXBuilder();
            builder.setEntityResolver(new IgnoreDTDEntityResolver());
            Element root = builder.build(path).getRootElement();

            String composerString = JDOMUtils.getChildTextIfExists(root, "identification", "creator");
            Composer composer = composerString == null? null : new Composer(composerString.split(" ")[0], composerString.split(" ")[1]);
            return new Song(
                    JDOMUtils.getChildTextIfExists(root, "work", "work-title"),
                    composer,
                    "style",
                    root.getChildren("part").stream()
                    .map(part -> new PartBuilder(part).build()).toList()
            );
        } catch (IOException | JDOMException e) {
            throw new RuntimeException("could not read " + path, e);
        }
    }

    public Collection<Chart> toCharts(String path) {
        Song song = readSong(path);
        return song.parts().stream().map(p -> new Chart(song, p)).toList();
    }
}