package music;

import java.util.ArrayList;
import java.util.List;

public record Song(String title, Composer composer, String style, List<Part> parts) {

    public Song(String title, Composer composer, String style, List<Part> parts) {
        this.title = title;
        this.composer = composer;
        this.style = style;
        this.parts = new ArrayList<>(parts);
    }
}
