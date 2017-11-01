package pw.phylame.commons.value;

import lombok.NonNull;

import java.lang.ref.Reference;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Value<T> {
    T get();

    default <R> Value<R> map(@NonNull Function<? super T, ? extends R> transform) {
        return () -> transform.apply(get());
    }

    static <T> Value<T> of(@NonNull Reference<T> reference) {
        return reference::get;
    }

    static <T> Value<T> of(@NonNull Supplier<T> supplier) {
        return supplier::get;
    }

    static <T> Value<T> of(T value) {
        return () -> value;
    }
}
