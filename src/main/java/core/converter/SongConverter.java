package core.converter;

import core.exportable.Exportable;
import music.Song;

public interface SongConverter {
    Exportable convert(Song song);
}
