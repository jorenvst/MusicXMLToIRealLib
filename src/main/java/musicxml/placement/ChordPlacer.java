package musicxml.placement;

import music.Chord;
import music.Measure;

import java.util.Map;

public interface ChordPlacer {
    Map<Integer, Chord> placeChords(Measure measure);
}
