package irealpro;

import music.Harmony;
import music.Measure;
import music.Part;
import music.Song;

public class Chart {

    private final Song song;
    private final Part part;

    public Chart(Song song, Part part) {
        this.song = song;
        this.part = part;
    }

    public void export(String path) {

    }

    @Override
    public String toString() {
        // more info on iReal Pro file format: https://www.irealpro.com/ireal-pro-custom-chord-chart-protocol
        StringBuilder builder = new StringBuilder();
        builder.append("irealbook://").append(song.getTitle()).append("=")
                .append(song.getComposer().lastName()).append(" ").append(song.getComposer().firstName()).append("=")
                .append(song.getStyle()).append("=")
                .append(part.getKey()).append("=n=");

        for (Measure measure : part.getMeasures()) {
            if (measure.isImplicit()) continue;
            builder.append("|");
            for (Harmony harmony : measure.getHarmony()) {
                builder.append(harmony.getRoot()).append(harmony.getKind());
            }
        }
        builder.append("Z");

        return builder.toString();
    }
}
