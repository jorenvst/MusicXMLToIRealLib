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
