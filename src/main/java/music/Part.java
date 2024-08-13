package music;

import java.util.List;

public record Part(String key, int divisions, List<Measure> measures) {

}
