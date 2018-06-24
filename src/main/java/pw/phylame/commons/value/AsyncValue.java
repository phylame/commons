package pw.phylame.commons.value;

import lombok.NonNull;
import pw.phylame.commons.NestedException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public abstract class AsyncValue<T> implements Value<T> {
    private T value;

    private volatile boolean done = false;

    private volatile Future<T> future = null;

    private final Object lock = new Object();

    private final Callable<T> action = () -> {
        if (!done) {
            synchronized (lock) {
                if (!done) {
                    value = handleGet();
                    future = null;
                    done = true;
                }
            }
        }
        return value;
    };

    protected abstract T handleGet();

    @Override
    public final T get() {
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

    public final void schedule(@NonNull ExecutorService service) {
        if (future != null) {
            throw new IllegalStateException("Already submitted");
        }
        future = service.submit(action);
    }

    public final void reset() {
        done = false;
    }
}
