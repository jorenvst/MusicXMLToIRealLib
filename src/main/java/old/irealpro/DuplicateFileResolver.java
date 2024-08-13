package old.irealpro;

/**
 * a functional interface for determining what to do when trying to write a chart to a file while the file already exists
 */
@FunctionalInterface
public interface DuplicateFileResolver {

    /**
     * @return true if the file should be overridden, false if not
     */
    boolean override();
}
