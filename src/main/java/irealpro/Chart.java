package irealpro;

import music.*;
import music.BarLine.BarLinePosition;

public class Chart {

    private final Song song;
    private final Part part;

    public Chart(Song song, Part part) {
        this.song = song;
        this.part = part;
    }

    public void export(String path) {
        // TODO: implement
    }

    @Override
    public String toString() {
        // more info on iReal Pro file format: https://www.irealpro.com/ireal-pro-custom-chord-chart-protocol
        StringBuilder builder = new StringBuilder();
        // add song metadata
        builder.append("irealbook://").append(song.getTitle()).append("=")
                .append(song.getComposer().lastName()).append(" ").append(song.getComposer().firstName()).append("=")
                .append(song.getStyle()).append("=")
                .append(part.getKey()).append("=n=");

        // add each measure to the url
        Time time = null;
        for (Measure measure : part.getMeasures()) {
            if (measure.isImplicit()) continue;

            // only explicitly add the time signature if it differs from the previous measure
            if (time == null || measure.getTime() != time) {
                builder.append(measure.getTime());
            }
            time = measure.getTime();

            builder.append(measure.getStartBarLine().symbol(BarLinePosition.START));

            // if a measure has no harmony, repeat the previous chord
            if (measure.getHarmony().isEmpty()) {
                builder.append(" x ");
            }

            // add the harmony
            // TODO: bring offset into consideration instead of just adding harmony in order
            for (Harmony harmony : measure.getHarmony()) {
                builder.append(harmony.getRoot()).append(harmony.getKind()).append(" ");
            }

            // append special bar line at the end if it is not regular
            if (measure.getEndBarLine() != BarLine.REGULAR) {
                builder.append(measure.getEndBarLine().symbol(BarLinePosition.END));
            }
        }

        return builder.toString();
    }
}
