package core.exportable;

@FunctionalInterface
public interface DuplicateFileResolver {

    /**
     * function for determining if a file should be overridden when exporting an Exportable
     * @return true if the file should be overridden, false if not
     */
    boolean resolve();
}
