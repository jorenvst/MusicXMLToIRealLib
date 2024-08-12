package music;

import java.util.List;

public class Song {

    private final String title;
    private final Composer composer;
    private final String style;

    private final List<Part> parts;

    public Song(String title, Composer composer, String style, List<Part> parts) {
        this.title = title;
        this.composer = composer;
        this.style = style;

        this.parts = parts;
    }

    public String getTitle() {
        return title;
    }

    public Composer getComposer() {
        return composer;
    }

    public String getStyle() {
        return style;
    }

    public List<Part> getParts() {
        return parts;
    }
}
