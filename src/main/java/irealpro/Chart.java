package irealpro;

import music.*;
import music.BarLine.BarLinePosition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * represents an IRealPro chord chart
 */
public class Chart {

    private DuplicateFileResolver resolver;

    private final Song song;
    private final Part part;

    /**
     * @param song the song where the chart is a part of
     *             used for metadata
     * @param part the chart will be created from this part
     *             used for constructing the body of the chart
     */
    public Chart(Song song, Part part) {
        this.song = song;
        this.part = part;

        this.resolver = () -> false;
    }

    /**
     * set functional interface DuplicateFileResolver for resolving conflicts when trying to export to a file
     * @param resolver implementation that needs to be used
     */
    public void setDuplicateFileResolver(DuplicateFileResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * export this chart to a .html file
     * more info on IReal Pro file format: <a href="https://www.irealpro.com/ireal-pro-custom-chord-chart-protocol">format</a>
     * @param path place where the file should be saved, is not used currently
     */
    // TODO: use path
    public void export(String path) {
        String fileName = song.getTitle() + ".html";
        File export = new File(fileName);

        if (Files.exists(Path.of(export.getAbsolutePath()))) {
            resolveDuplicate(path, export);
        } else {
            write(fileName);
        }
    }

    /**
     * @return the body of the html tag in IReal Pro file format
     */
    @Override
    public String toString() {
        return buildMetaData() + buildBody();
    }

    /**
     * write the chart to a file
     * @param fileName the filename to write to
     */
    private void write(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("<a href=\"" + this + "\">" + song.getTitle() + "</a>");
            System.out.println("export success");
        } catch (IOException e) {
            System.out.println("could not create file");
        }
    }

    /**
     * calls the resolver.override() method to check what to do when the file it wants to export to, already exists
     * if this returns true, the already existing file will be overridden
     * @param path place to export to, not used currently
     * @param export the already existing file
     */
    private void resolveDuplicate(String path, File export) {
        if (resolver.override()) {
            System.out.println("overriding existing file: " + export.getAbsolutePath());
            if (export.delete()) {
                export(path);
            }
        } else {
            System.out.println("file already exists: " + export.getAbsolutePath());
        }
    }

    /**
     * @return the header for the custom IReal Pro URL containing its metadata
     */
    private String buildMetaData() {
        return "irealbook://" + song.getTitle() + "=" +
                song.getComposer().lastName() + " " + song.getComposer().firstName() + "=" +
                song.getStyle() + "=" +
                part.getKey() + "=n=";
    }

    /**
     * @return the chart itself containing the chord progression in the correct IReal Pro format
     */
    private String buildBody() {
        StringBuilder builder = new StringBuilder();
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
