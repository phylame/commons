package pw.phylame.commons.io;

import lombok.Synchronized;

public abstract class DisposableSupport implements AutoDisposable {
    private volatile int refCount = 1;

    @Override
    @Synchronized
    public void retain() {
        if (refCount == 0) {
            throw new DisposedException();
        }
        ++refCount;
    }

    @Override
    @Synchronized
    public void release() {
        if (refCount == 0) {
            throw new DisposedException();
        }
        if (--refCount == 0) {
            dispose();
        }
    }

    protected abstract void dispose();
}
