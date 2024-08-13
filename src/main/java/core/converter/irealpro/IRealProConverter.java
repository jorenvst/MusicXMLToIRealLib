package core.converter.irealpro;

import core.converter.SongConverter;
import core.exportable.irealpro.Chart;
import music.*;

import java.io.IOException;
import java.util.Properties;

public class IRealProConverter implements SongConverter {

    private final Properties timeProperties;

    public IRealProConverter() {
        try {
            timeProperties = new Properties();
            timeProperties.load(IRealProConverter.class.getResourceAsStream("time.properties"));
        } catch (IOException e) {
            throw new RuntimeException("could not read time.properties", e);
        }
    }

    @Override
    public Chart convert(Song song) {
        Part part = song.parts().getFirst();
        return new Chart(song.title(), song.composer(), song.style(), part.key(), constructBody(part));
    }

    private String constructBody(Part part) {
        StringBuilder body = new StringBuilder();
        Time time = null;
        for (Measure measure : part.measures()) {
            if (measure.isImplicit()) continue;

            if (time == null || !measure.time().equals(time)) {
                body.append(timeProperties.getProperty(measure.time() + ""));
            }
            time = measure.time();

            // TODO: implement barlines
            body.append("|");

            if (measure.harmony().isEmpty()) {
                body.append(" x  ");
            }

            for (int i = 0; i < measure.time().beats(); i++) {
                // when no harmony is present on a beat, insert a space
                // this might cause issues with weird chord placements like T44|C7  G^7Z
                Harmony harmony = measure.harmony().getOrDefault(i, new Harmony(" ", ""));
                body.append(harmony.root()).append(harmony.kind());
            }
        }
        body.append("Z");
        return body + "";
    }
}
