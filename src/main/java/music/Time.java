package music;

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Time time)) {
            return false;
        }
        return time.beats() == this.beats && time.beatType() == this.beatType;
    }
}
