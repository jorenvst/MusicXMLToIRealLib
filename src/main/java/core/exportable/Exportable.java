package core.exportable;

public interface Exportable {

    /**
     * export this Exportable into a file
     * @param path the place to save this file to
     */
    void export(String path);

    /**
     * export this Exportable into a file into the current working directory
     */
    default void export() {
        export(System.getProperty("user.dir"));
    }

    void setResolver(DuplicateFileResolver resolver);
}
