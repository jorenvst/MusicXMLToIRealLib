package music;

import musicxml.Repetition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Measure --- represents a measure in music
 * created when musicxml file is read
 */
public class Measure {

    private final List<Chord> chords;
    private final List<Note> notes;
    private final Time time;
    private final String barLineType;
    private final boolean implicit;
    private final Repetition repetition;
    private final int divisions;

    /**
     * @param chords        chords in this measure
     * @param time          time signature of this measure
     * @param barLineType   type of bar line, e.g. double, regular, ...
     * @param implicit      don't use this measure for conversion if implicit is true
     * @param repetition    type of repetition for this bar line, e.g. none, right, left
     */
    public Measure(Collection<Chord> chords, Collection<Note> notes, Time time, String barLineType, boolean implicit, Repetition repetition, int divisions) {
        this.chords = new ArrayList<>(chords);
        this.notes = new ArrayList<>(notes);
        this.time = time;
        if (repetition == Repetition.NONE) {
            this.barLineType = barLineType;
        } else {
            this.barLineType = "regular";
        }
        this.implicit = implicit;
        this.repetition = repetition;
        this.divisions = divisions;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public String getBarLineType() {
        return barLineType;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public Time getTime() {
        return time;
    }

    /**
     * check if this time signature has a measure change
     */
    public boolean hasTime() {
        return time != null;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public boolean isFull() {
        return notes.stream().mapToInt(Note::duration).sum() == time.beats() * divisions;
    }

    @Override
    public String toString() {
        return chords.toString();
    }
}
