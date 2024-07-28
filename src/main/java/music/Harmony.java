package music;

public class Harmony {

    private final String root;
    private final String kind;
    private final String bass;
    private final int offset;

    public Harmony(String root, String kind) {
        this(root, kind, "", 0);
    }

    public Harmony(String root, String kind, String bass, int offset) {
        this.root = root;
        this.kind = kind;
        this.bass = bass;
        this.offset = offset;
    }

    @Override
    public String toString() {
        if (bass.isEmpty()) {
            return root + kind;
        } else {
            return root + kind + "/" + bass;
        }
    }
}
