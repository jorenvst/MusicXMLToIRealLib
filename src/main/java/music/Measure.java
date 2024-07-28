package music;

import java.util.ArrayList;
import java.util.List;

public class Measure {

    private final Time time;
    private final List<Harmony> harmony;

    private final BarLine start;
    private final BarLine end;

    public Measure(Time time) {
        this(time, new ArrayList<>(), BarLine.REGULAR, BarLine.REGULAR);
    }

    public Measure(Time time, List<Harmony> harmony, BarLine start, BarLine end) {
        this.time = time;
        this.harmony = harmony;

        this.start = start;
        this.end = end;
    }

    public void addHarmony(Harmony chord) {
        harmony.add(chord);
    }

    @Override
    public String toString() {
        return time + " " + harmony;
    }
}
