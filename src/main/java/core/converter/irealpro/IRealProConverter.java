package core.converter.irealpro;

import core.converter.SongConverter;
import core.exportable.irealpro.Chart;
import music.*;
import util.PropertiesSupplier;
import util.PropertiesType;

public class IRealProConverter implements SongConverter {

    private final PropertiesSupplier supplier;

    /**
     * class for converting Song into a Chart
     */
    public IRealProConverter() {
        supplier = new PropertiesSupplier();
    }

    /**
     * convert a song into a Chart
     * @param song the song to convert
     * @return the Chart
     */
    @Override
    public Chart convert(Song song) {
        Part part = song.parts().getFirst();
        return new Chart(song.title(), song.composer(), song.style(), part.key(), constructBody(part));
    }

    /**
     * construct the body of the IReal Pro chart in the correct format
     * @param part the part to convert
     * @return the converted body in the correct format for IReal Pro
     */
    private String constructBody(Part part) {
        StringBuilder body = new StringBuilder();
        Time time = null;
        for (Measure measure : part.measures()) {
            if (measure.isImplicit()) continue;

            if (time == null || !measure.time().equals(time)) {
                body.append(supplier.getProperty(PropertiesType.TIME, measure.time() + ""));
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
