package core.reader;

import music.Song;

import java.io.File;

public interface SongReader {
    Song read(File file);
}
