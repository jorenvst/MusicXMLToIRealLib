package music;

import java.util.ArrayList;
import java.util.List;

public record Part(String key, int divisions, List<Measure> measures) {

    public Part(String key, int divisions, List<Measure> measures) {
        this.key = key;
        this.divisions = divisions;
        this.measures = new ArrayList<>(measures);
    }

}
