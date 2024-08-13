package old.irealpro;

import old.music.Song;
import old.music.part.Part;
import old.music.part.measure.BarLine;
import old.music.part.measure.Harmony;
import old.music.part.measure.Measure;
import old.music.part.measure.Time;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        // do not override existing files by default when exporting
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
     * export this chart to a .html file in the current working directory
     */
    public void export() {
        export(System.getProperty("user.dir"));
    }

    /**
     * export this chart to a .html file
     * more info on IReal Pro file format: <a href="https://www.irealpro.com/ireal-pro-custom-chord-chart-protocol">format</a>
     * @param path place where the file should be saved
     */
    public void export(String path) {
        if (path.charAt(path.length() - 1) != '/'){
            path += "/";
        }
        String fileName = path + song.title() + ".html";
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
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName), Charset.defaultCharset())) {
            writer.write("<a href=\"" + this + "\">" + song.title() + "</a>");
            System.out.println("export succes");
        } catch (IOException e) {
            System.out.println("could not create file");
            System.out.println(e + "");
        }
    }

    /**
     * calls the resolver.override() method to check what to do when the file it wants to export to, already exists
     * if this returns true, the already existing file will be overridden
     * @param path place to export to
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
        return "irealbook://" + song.title() + "=" +
                song.composer().lastName() + " " + song.composer().firstName() + "=" +
                song.style() + "=" +
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

            builder.append(measure.getStartBarLine().symbol(BarLine.BarLinePosition.START));

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
                builder.append(measure.getEndBarLine().symbol(BarLine.BarLinePosition.END));
            }
        }
        return builder.toString();
    }
}
