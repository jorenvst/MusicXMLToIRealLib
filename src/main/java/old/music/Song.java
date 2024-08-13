package old.music;

import old.music.part.Part;

import java.util.List;

/**
 *
 * @param title title of the song
 * @param composer composer of the song
 * @param style style of the song, this is unused currently
 * @param parts a song can be built out of several parts, with each part containing a score
 */
public record Song(String title, Composer composer, String style, List<Part> parts) {

}
