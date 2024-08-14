package music;

public class Harmony {

    private final String root;
    private final String kind;
    private final String bass;

    public Harmony(String root, String kind) {
        this(root, kind, null);
    }

    /**
     * represents a chord
     * @param root root note of the chord, e.g. E in E^⁷#11/G#
     * @param kind the kind of the chord, including extensions, e.g. ^⁷#11 inn E^⁷#11/G#
     * @param bass the note that should be played in the bass, e.g. G# in E^⁷#11/G#
     */
    public Harmony(String root, String kind, String bass) {
        this.root = root;
        this.kind = kind;
        this.bass = bass;
    }

    public String root() {
        return root;
    }

    public String kind() {
        return kind;
    }

    public String bass() {
        return bass;
    }

    @Override
    public String toString() {
        return root + kind;
    }
}
