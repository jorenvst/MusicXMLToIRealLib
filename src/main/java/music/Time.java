package music;

/**
 * represents a time signature
 * @param beats the number of beats, e.g. 3 in 3/4
 * @param subdivision the subdivision, e.g. 4 in 3/4
 */
public record Time(int beats, int subdivision) {
    @Override
    public String toString() {
        return beats + "/" + subdivision;
    }
}
