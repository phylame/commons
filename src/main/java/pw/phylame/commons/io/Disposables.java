package pw.phylame.commons.io;

import lombok.val;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Disposables {
    public static void retain(Object obj) {
        if (obj != null) {
            if (obj instanceof Disposable) {
                ((Disposable) obj).retain();
            } else if (obj instanceof Iterable<?>) {
                for (val o : (Iterable<?>) obj) {
                    retain(o);
                }
            }
        }
    }

    public static void release(Object obj) {
        if (obj != null) {
            if (obj instanceof Disposable) {
                ((Disposable) obj).release();
            } else if (obj instanceof Iterable<?>) {
                for (val o : (Iterable<?>) obj) {
                    release(o);
                }
            }
        }
    }
}
