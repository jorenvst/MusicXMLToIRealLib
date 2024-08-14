package core;

import core.exportable.Exportable;

import java.io.File;

public class Converter {

    /**
     * utility class for converting files from one format to another
     */
    public Converter() {

    }
    
    /**
     * converts a file into an Exportable format
     * @param from format of the file
     * @param to format of the Exportable
     * @param file the file that needs to be converted
     * @return an Exportable format
     */
    public static Exportable convert(FromFormat from, ToFormat to, File file) {
        return to.converter().convert(from.reader().read(file));
    }

    /**
     * converts a file into an Exportable and saves it to the current working directory
     * @param from format of the file
     * @param to format of the Exportable
     * @param file the file that needs to be converted
     */
    public static void convertThenExport(FromFormat from, ToFormat to, File file) {
        convert(from, to, file).export();
    }

    /**
     * converts a file into an Exportable and saves it to the specified path
     * @param from format of the file
     * @param to format of the Exportable
     * @param file the file that needs to be converted
     * @param path place where the file should be exported to
     */
    public static void convertThenExport(FromFormat from, ToFormat to, File file, String path) {
        convert(from, to, file).export(path);
    }
}
