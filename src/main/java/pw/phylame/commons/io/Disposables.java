package pw.phylame.commons.io;

import lombok.val;

public final class Disposables {
    private Disposables() {
    }

    public static void retain(Object obj) {
        if (obj instanceof AutoDisposable) {
            ((AutoDisposable) obj).retain();
        }
    }

    public static void retainAll(Iterable<?> it) {
        if (it != null) {
            for (val obj : it) {
                retain(obj);
            }
        }
    }

    public static void release(Object obj) {
        if (obj instanceof AutoDisposable) {
            ((AutoDisposable) obj).release();
        }
    }

    public static void releaseAll(Iterable<?> it) {
        if (it != null) {
            for (val obj : it) {
                release(obj);
            }
        }
    }
}
