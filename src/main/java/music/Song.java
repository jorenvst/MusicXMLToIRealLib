package music;

import java.util.ArrayList;
import java.util.List;

public record Song(String title, Composer composer, String style, List<Part> parts) {

    /**
     * represents a Song containing some metadata and a list of Parts
     * @param title the title of this Song
     * @param composer the composer of this song
     * @param style the style of this song, currently unused when importing from MusicXML
     * @param parts a list of Parts
     */
    public Song(String title, Composer composer, String style, List<Part> parts) {
        this.title = title;
        this.composer = composer;
        this.style = style;
        this.parts = new ArrayList<>(parts);
    }
}
