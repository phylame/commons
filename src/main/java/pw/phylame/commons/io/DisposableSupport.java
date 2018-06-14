package pw.phylame.commons.io;

import pw.phylame.commons.NestedException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public abstract class DisposableSupport implements Disposable {
    private final AtomicInteger counter = new AtomicInteger(1);

    private final Object lock = new Object();

    @Override
    public final void retain() throws DisposedException {
        if (counter.get() == 0) {
            throw new DisposedException();
        }
        synchronized (lock) {
            if (counter.get() == 0) {
                throw new DisposedException();
            }
            counter.incrementAndGet();
        }
    }

    @Override
    public final void release() throws NestedException {
        if (counter.get() > 0) {
            synchronized (lock) {
                if (counter.get() > 0 && counter.decrementAndGet() == 0) {
                    try {
                        close();
                    } catch (Exception e) {
                        throw new NestedException(e);
                    }
                }
            }
        }
    }
}
