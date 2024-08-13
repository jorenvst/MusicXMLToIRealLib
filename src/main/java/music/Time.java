package music;

public record Time(int beats, int subdivision) {
    @Override
    public String toString() {
        return beats + "/" + subdivision;
    }
}
