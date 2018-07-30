package pw.phylame.commons.io;

import lombok.val;
import pw.phylame.commons.NestedException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Disposables {
    public static <T> T retain(T obj) throws DisposedException {
        if (obj instanceof Disposable) {
            ((Disposable) obj).retain();
        }
        return obj;
    }

    public static <T> T retainAll(T obj) throws DisposedException {
        if (obj instanceof Iterable<?>) {
            for (val e : (Iterable<?>) obj) {
                retain(e);
            }
        }
        return obj;
    }

    public static <T> T release(T obj) throws NestedException {
        if (obj instanceof Disposable) {
            ((Disposable) obj).release();
        }
        return obj;
    }

    public static <T> T releaseAll(T obj) throws NestedException {
        if (obj instanceof Iterable<?>) {
            for (val e : (Iterable<?>) obj) {
                release(e);
            }
        }
        return obj;
    }
}
