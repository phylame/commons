package pw.phylame.commons.io;

import pw.phylame.commons.NestedException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface Disposable extends AutoCloseable {
    void retain() throws DisposedException;

    void release() throws NestedException;

    static <C extends AutoCloseable> DisposableWrapper<C> of(C source) {
        return new DisposableWrapper<>(source);
    }
}
