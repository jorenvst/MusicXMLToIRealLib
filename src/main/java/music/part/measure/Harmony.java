package music.part.measure;

public class Harmony {

    private final String root;
    private final String kind;
    private final String bass;
    private final int offset;

    public Harmony(String root, String kind, int offset) {
        this(root, kind, "", offset);
    }

    /**
     * represents a chord in a measure with its placement
     * @param root the root note of the chord, e.g. E in E^7#11/G#
     * @param kind the kind of the chord, e.g. ^7#11 in E^7#11/G#
     * @param bass the note that should be played in the bass, e.g. G# in E^7#11/G#
     * @param offset the place of the chord in the measure, in division units
     *               the measure that contains this harmony keeps track of the number of divisions
     */
    public Harmony(String root, String kind, String bass, int offset) {
        this.root = root;
        this.kind = kind;
        this.bass = bass;
        this.offset = offset;
    }

    public String getRoot() {
        return root;
    }

    public String getKind() {
        return kind;
    }

    public String getBass() {
        return bass;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        if (bass.isEmpty()) {
            return root + kind + ":" + offset;
        } else {
            return root + kind + "/" + bass;
        }
    }
}
