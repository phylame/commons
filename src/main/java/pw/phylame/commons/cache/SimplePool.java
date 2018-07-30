package pw.phylame.commons.cache;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.Validate;

public class SimplePool<T> implements Pool<T> {
    private final Object[] pool;

    private int size;

    public SimplePool(int capacity) {
        Validate.require(capacity >= 0, "The capacity must be >= 0");
        pool = new Object[capacity];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T acquire() {
        if (size > 0) {
            val index = --size;
            val instance = (T) pool[index];
            pool[index] = null;
            return instance;
        }
        return null;
    }

    @Override
    public boolean release(@NonNull T instance) {
        if (contains(instance)) {
            throw new IllegalStateException("Already in the pool");
        }
        if (size < pool.length) {
            pool[size++] = instance;
            return true;
        }
        return false;
    }

    private boolean contains(T instance) {
        for (int i = 0; i < size; i++) {
            if (pool[i] == instance) {
                return true;
            }
        }
        return false;
    }
}
