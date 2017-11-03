package pw.phylame.commons.io;

public interface AutoDisposable {
    void retain();

    void release();
}
