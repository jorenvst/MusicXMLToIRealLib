package core.exportable;

@FunctionalInterface
public interface DuplicateFileResolver {
    boolean resolve();
}
