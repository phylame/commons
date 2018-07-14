package pw.phylame.commons.cache;

import lombok.NonNull;
import lombok.Synchronized;

public class SynchronizedPool<T> extends SimplePool<T> {
    public SynchronizedPool(int capacity) {
        super(capacity);
    }

    @Override
    @Synchronized
    public T acquire() {
        return super.acquire();
    }

    @Override
    @Synchronized
    public boolean release(@NonNull T element) {
        return super.release(element);
    }
}
