package music;

public class Harmony {

    private final String root;
    private final String kind;
    private final String bass;

    public Harmony(String root, String kind) {
        this(root, kind, null);
    }

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
}
