package music;

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
