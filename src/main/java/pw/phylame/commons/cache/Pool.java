package pw.phylame.commons.cache;

public interface Pool<T> {
    T acquire();

    boolean release(T instance);
}
