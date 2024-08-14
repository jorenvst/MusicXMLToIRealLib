package core.exportable.irealpro;

import core.exportable.Exportable;
import music.Composer;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Chart implements Exportable {

    private final String title;
    private final Composer composer;
    private final String style;
    private final String key;

    private final String body;

    public Chart(String title, Composer composer, String style, String key, String body) {
        this.title = title;
        this.composer = composer;
        this.style = style;
        this.key = key;

        this.body = body;
    }

    @Override
    public void export(String path) {
        if (path.charAt(path.length() - 1) != '/'){
            path += "/";
        }

        String fileName = path + title + ".html";

        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName), Charset.defaultCharset())) {
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
