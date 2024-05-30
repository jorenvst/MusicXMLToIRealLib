package converter.music;

/**
 * Time --- represents a time signature
 * @param beats how many beats there are in a measure
 * @param beatType the subdivision of beats
 */
public record Time(int beats, int beatType) {

    @Override
    public String toString() {
        return beats + "/" + beatType;
    }
}
