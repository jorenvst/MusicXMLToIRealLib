package musicxml.placement;

import music.Chord;
import music.Measure;

import java.util.HashMap;
import java.util.Map;

public class DefaultChordPlacer implements ChordPlacer {

    @Override
    public Map<Integer, Chord> placeChords(Measure measure) {
        // places each chord in a measure to a beat
        Map<Integer, Chord> chordMap = new HashMap<>();
        for (int i = 0; i < measure.getChords().size(); i++) {
            chordMap.put(i, measure.getChords().get(i));
        }
        return chordMap;
    }
}
