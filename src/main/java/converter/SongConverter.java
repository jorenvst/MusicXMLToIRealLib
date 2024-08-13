package converter;

import exportable.Exportable;
import music.Song;

public interface SongConverter {
    Exportable convert(Song song);
}
