package core.exportable.irealpro;

import core.exportable.DuplicateFileResolver;
import core.exportable.Exportable;
import music.Composer;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Chart implements Exportable {

    private final String title;
    private final Composer composer;
    private final String style;
    private final String key;

    private final String body;

    private DuplicateFileResolver resolver;

    /**
     *
     * @param title title of this Chart
     * @param composer composer of this Chart
     * @param style the style of music, currently unused when a Song is constructed from MusicXML
     * @param key the key of this song, e.g. Ab
     * @param body the song itself in IReal Pro notation
     */
    public Chart(String title, Composer composer, String style, String key, String body) {
        this.title = title;
        this.composer = composer;
        this.style = style;
        this.key = key;

        this.body = body;
        this.resolver = () -> false;
    }

    public void setResolver(DuplicateFileResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * export this Chart to a file specified by path
     * @param path the place to save this file to
     */
    @Override
    public void export(String path) {
        if (path.charAt(path.length() - 1) != '/'){
            path += "/";
        }

        String fileName = path + title + ".html";
        Path filePath = Paths.get(fileName);

        if (Files.exists(filePath)) {
            System.out.println("this file already exists");
            if (resolver.resolve()) {
                try {
                    Files.delete(filePath);
                } catch (IOException e) {
                    System.out.println("could not delete file");
                }
            } else {
                return;
            }
        }

        try (Writer writer = Files.newBufferedWriter(filePath, Charset.defaultCharset())) {
            writer.write(this + "");
            System.out.println("export succes");
        } catch (IOException e) {
            System.out.println("could not create file");
            System.out.println(e + "");
        }
    }

    @Override
    public String toString() {
        return "<a href=\"irealbook://"
                + title + "="
                + composer.lastName() + " " + composer.firstName() + "="
                + style + "="
                + key + "=n="
                + body + "\">"
                + title +
                "</a>";
    }
}
