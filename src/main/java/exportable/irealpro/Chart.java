package exportable.irealpro;

import exportable.Exportable;
import music.Composer;

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
        throw new RuntimeException("not implemented");
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
