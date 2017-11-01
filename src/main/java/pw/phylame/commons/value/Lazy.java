package pw.phylame.commons.value;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public final class Lazy<T> implements Value<T> {
    @NonNull
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
