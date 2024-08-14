package core;

import core.exportable.Exportable;

import java.io.File;

public class Converter {
    public static Exportable convert(FromFormat from, ToFormat to, File file) {
        return to.converter().convert(from.reader().read(file));
    }

    public static void convertThenExport(FromFormat from, ToFormat to, File file) {
        convert(from, to, file).export();
    }

    public static void convertThenExport(FromFormat from, ToFormat to, File file, String path) {
        convert(from, to, file).export(path);
    }
}
