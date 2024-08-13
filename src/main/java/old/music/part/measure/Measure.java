package old.music.part.measure;

import java.util.ArrayList;
import java.util.List;

public class Measure {

    private final Time time;
    private final int divisions;
    private final List<Harmony> harmony;
    private final boolean implicit;

    private final BarLine start;
    private final BarLine end;

    public Measure(Time time, int divisions, boolean implicit) {
        this(time, divisions, implicit, new ArrayList<>(), BarLine.REGULAR, BarLine.REGULAR);
    }

    public Measure(Time time, int divisions, boolean implicit, BarLine start, BarLine end) {
        this(time, divisions, implicit, new ArrayList<>(), start, end);
    }

    /**
     * represents a measure in a part
     * @param time time signature for this measure
     * @param divisions the number of divisions a subdivision of a measure is split into (depends on time signature),
     *                  e.g. divisions = 2 in 4/4, then the measure will count 8 divisions
     * @param implicit if a measure is implicit, it usually is a pickup measure
     * @param harmony all the chords in this measure
     * @param start the starting barline
     * @param end the ending barline
     */
    public Measure(Time time, int divisions, boolean implicit, List<Harmony> harmony, BarLine start, BarLine end) {
        this.time = time;
        this.divisions = divisions;
        this.harmony = harmony;
        this.implicit = implicit;

        this.start = start;
        this.end = end;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public void addHarmony(Harmony chord) {
        harmony.add(chord);
    }

    public List<Harmony> getHarmony() {
        return harmony;
    }

    public Time getTime() {
        return time;
    }

    public BarLine getStartBarLine() {
        return start;
    }

    public BarLine getEndBarLine() {
        return end;
    }

    @Override
    public String toString() {
        return divisions * time.getBeats() + ":" + time + " " + harmony;
    }
}
