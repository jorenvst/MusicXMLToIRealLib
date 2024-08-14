package core.converter;

import core.exportable.Exportable;
import music.Song;

public interface SongConverter {

    /**
     * converts a Song into an Exportable
     * @param song the song to convert
     * @return an Exportable
     */
    Exportable convert(Song song);
}
