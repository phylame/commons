package pw.phylame.commons.io;

import lombok.val;
import pw.phylame.commons.NestedException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Disposables {
    public static <T> T retain(T obj) throws DisposedException {
        if (obj != null) {
            if (obj instanceof Disposable) {
                ((Disposable) obj).retain();
            } else if (obj instanceof Iterable<?>) {
                for (val o : (Iterable<?>) obj) {
                    retain(o);
                }
            }
        }
        return obj;
    }

    public static <T> T release(T obj) throws NestedException {
        if (obj != null) {
            if (obj instanceof Disposable) {
                ((Disposable) obj).release();
            } else if (obj instanceof Iterable<?>) {
                for (val o : (Iterable<?>) obj) {
                    release(o);
                }
            }
        }
        return obj;
    }
}
