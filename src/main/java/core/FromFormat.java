package core;

import core.reader.SongReader;
import core.reader.musicxml.MusicXMLReader;

public enum FromFormat {
    MUSICXML(new MusicXMLReader());

    private final SongReader reader;

    FromFormat(SongReader reader) {
        this.reader = reader;
    }

    public SongReader reader() {
        return reader;
    }
}
