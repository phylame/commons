package pw.phylame.commons.value;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

/**
 * Synchronized lazy value.
 *
 * @param <T> type of value
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
@RequiredArgsConstructor(access = PRIVATE)
public final class Lazy<T> implements Value<T> {
    public static <T> Lazy<T> of(@NonNull Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    private final Supplier<? extends T> supplier;

    @Getter
    private volatile boolean initialized;

    private T value;

    @Override
    public T get() {
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    value = supplier.get();
                    initialized = true;
                }
            }
        }
        return value;
    }
}
