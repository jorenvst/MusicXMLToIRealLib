package core.reader;

import music.Song;

import java.io.File;

public interface SongReader {

    /**
     * read a Song from a file
     * @param file the file to read
     * @return a Song object containing the info from the file
     */
    Song read(File file);
}
