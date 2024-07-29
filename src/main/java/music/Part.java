package music;

import java.util.ArrayList;
import java.util.List;

public class Part {

    private final List<Measure> measures;
    private int divisions;

    public Part() {
        measures = new ArrayList<>();
        divisions = 1;
    }

    public Part(List<Measure> measures, int divisions) {
        this.measures = measures;
        this.divisions = divisions;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void addMeasure(Measure measure) {
        measures.add(measure);
    }

    public int getDivisions() {
        return divisions;
    }

    public void setDivisions(int divisions) {
        this.divisions = divisions;
    }

    @Override
    public String toString() {
        return String.join("\n", measures.stream().map(Measure::toString).toList());
    }
}
