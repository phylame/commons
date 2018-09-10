package pw.phylame.commons.value;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pw.phylame.commons.NestedException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@RequiredArgsConstructor
public final class Worker<T> implements Value<T> {
    @NonNull
    private final Supplier<T> supplier;

    private T value;

    private volatile boolean done = false;

    private volatile Future<T> future = null;

    private final Object lock = new Object();

    private final Callable<T> action = this::handleGet;

    private T handleGet() {
        if (!done) {
            synchronized (lock) {
                if (!done) {
                    value = supplier.get();
                    future = null;
                    done = true;
                }
            }
        }
        return value;
    }

    public void schedule(@NonNull ExecutorService service) {
        if (future != null) {
            throw new IllegalStateException("Already submitted");
        }
        future = service.submit(action);
    }

    public void reset() {
        done = false;
        future = null;
    }

    @Override
    public T get() {
        if (done) {
            return value;
        } else if (future != null) {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new NestedException(e);
            }
        } else {
            try {
                return action.call();
            } catch (Exception e) {
                throw new NestedException(e);
            }
        }
    }
}
