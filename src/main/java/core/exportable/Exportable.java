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

    /**
     * set a function that returns a boolean if a file should be overridden while exporting an Exportable
     * @param resolver the implementation to use
     */
    void setResolver(DuplicateFileResolver resolver);
}
