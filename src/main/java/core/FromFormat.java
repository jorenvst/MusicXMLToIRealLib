package core;

import core.reader.SongReader;
import core.reader.musicxml.MusicXMLReader;

public enum FromFormat {
    MUSICXML(new MusicXMLReader());

    private final SongReader reader;

    /**
     * used to specify from what file format a Song object should be constructed
     * @param reader implementation of SongReader for this type of file format
     */
    FromFormat(SongReader reader) {
        this.reader = reader;
    }

    /**
     * get the SongReader implementation
     * @return SongReader implementation
     */
    public SongReader reader() {
        return reader;
    }
}
