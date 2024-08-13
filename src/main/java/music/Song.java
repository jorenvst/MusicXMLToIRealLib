package music;

import java.util.List;

public record Song(String title, Composer composer, String style, List<Part> parts) {
}
