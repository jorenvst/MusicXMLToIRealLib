package music;

import java.util.HashMap;
import java.util.Map;

public class Measure {

    private final Time time;
    private final Map<Integer, Harmony> harmony;
    private final boolean implicit;

    public Measure(Time time, Map<Integer, Harmony> harmony) {
        this(time, harmony, false);
    }

    /**
     * represents a Measure
     * @param time time signature of this measure
     * @param harmony a Map containing the position of the chord mapped to the chord itself
     * @param implicit if a measure is implicit, it is mostly a pick-up measure and can be ignored in some cases
     */
    public Measure(Time time, Map<Integer, Harmony> harmony, boolean implicit) {
        this.time = time;
        this.harmony = new HashMap<>(harmony);
        this.implicit = implicit;
    }

    public Time time() {
        return time;
    }

    public Map<Integer, Harmony> harmony() {
        return harmony;
    }

    public boolean isImplicit() {
        return implicit;
    }

    @Override
    public String toString() {
        return time + " " + harmony.values();
    }
}
