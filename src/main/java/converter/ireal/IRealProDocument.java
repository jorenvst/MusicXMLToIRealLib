package converter.ireal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * IRealProDocument --- represents an IReal Pro document
 */
public class IRealProDocument {

    private final String content;
    private final String title;

    public IRealProDocument(String url, String songTitle) {
        this.content = "<a href=\"" + url + "\">" + songTitle + "</a>\n";
        this.title = songTitle;
    }

    /**
     * build an ireal pro file
     * @param path the directory where the file should be saved
     */
    public void build(String path) {
        try {
            String fileName = path + title + ".html";
            File irealFile = new File(fileName);

            if (irealFile.createNewFile()) {
                try (FileWriter writer = new FileWriter(irealFile)) {
                    writer.write(content);
                    System.out.println(fileName + " created successfully");
                }
            } else {
                System.out.println(fileName + " already exists in this directory");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create file", e);
        }
    }

    /**
     * build an ireal pro file in the current working directory
     */
    public void build() {
        build(System.getProperty("user.dir"));
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
