package music;

import java.util.ArrayList;
import java.util.List;

public class Measure {

    private final Time time;
    private final int divisions;
    private final List<Harmony> harmony;

    private final BarLine start;
    private final BarLine end;

    public Measure(Time time, int divisions) {
        this(time, divisions, new ArrayList<>(), BarLine.REGULAR, BarLine.REGULAR);
    }

    public Measure(Time time, int divisions, List<Harmony> harmony, BarLine start, BarLine end) {
        this.time = time;
        this.divisions = divisions;
        this.harmony = harmony;

        this.start = start;
        this.end = end;
    }

    public void addHarmony(Harmony chord) {
        harmony.add(chord);
    }

    @Override
    public String toString() {
        return divisions * time.getBeats() + ":" + time + " " + harmony;
    }
}
