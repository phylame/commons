package pw.phylame.commons;

public interface Describable {
    String getName();

    String getVersion();

    default String getDescription() {
        return "";
    }

    default String getVendor() {
        return "";
    }
}
