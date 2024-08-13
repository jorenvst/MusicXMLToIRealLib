package music.part;

import music.part.measure.Measure;

import java.util.ArrayList;
import java.util.List;

public class Part {

    private final List<Measure> measures;
    private int divisions;
    private String key;

    /**
     * represents a part of a song
     */
    public Part() {
        measures = new ArrayList<>();
        divisions = 1;
    }

    public Part setKey(String key) {
        this.key = key;
        return this;
    }

    public String getKey() {
        return key;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void addMeasure(Measure measure) {
        measures.add(measure);
    }

    @Override
    public String toString() {
        return String.join("\n", measures.stream().map(Measure::toString).toList());
    }
}
