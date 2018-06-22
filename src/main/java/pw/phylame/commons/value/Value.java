package pw.phylame.commons.value;

import lombok.NonNull;

import java.lang.ref.Reference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Wrapper for value.
 *
 * @param <T> type of actual value
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public interface Value<T> {
    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    T get();

    default <U> Value<U> map(@NonNull Function<? super T, ? extends U> mapper) {
        return () -> mapper.apply(get());
    }

    static <T> Value<T> of(T value) {
        return () -> value;
    }

    static <T> Value<T> of(@NonNull Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> Value<T> of(@NonNull Reference<T> reference) {
        return reference::get;
    }

    @SuppressWarnings("unchecked")
    static <T> T get(@NonNull Object obj) {
        return obj instanceof Value<?> ? ((Value<T>) obj).get() : (T) obj;
    }

    static <T> T get(Value<T> value) {
        return value != null ? value.get() : null;
    }
}