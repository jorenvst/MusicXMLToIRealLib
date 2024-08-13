package exportable;

public interface Exportable {

    void export(String path);

    default void export() {
        export(System.getProperty("user.dir"));
    }
}
