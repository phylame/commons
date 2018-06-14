package pw.phylame.commons;

import lombok.NonNull;
import lombok.Synchronized;
import lombok.val;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.function.Consumer;

import static pw.phylame.commons.CollectionUtils.isNotEmpty;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class EventBus {
    private final MultiMap<Class<?>, Consumer<?>> observers = new MultiMap<>(new IdentityHashMap<>(), ArrayList::new);

    @Synchronized
    public final <T> void register(@NonNull Class<T> type, @NonNull Consumer<T> observer) {
        observers.put(type, observer);
    }

    @Synchronized
    public final <T> void remove(Class<T> type, Consumer<T> observer) {
        observers.remove(type, observer);
    }

    @SuppressWarnings("unchecked")
    public final void post(@NonNull Object event) {
        post(event, (Class<Object>) event.getClass());
    }

    @Synchronized
    @SuppressWarnings("unchecked")
    public final <T> void post(T event, @NonNull Class<T> type) {
        val consumers = observers.getAll(type);
        if (isNotEmpty(consumers)) {
            for (val consumer : consumers) {
                if (event instanceof Consumable && ((Consumable) event).isConsumed()) {
                    break;
                } else {
                    ((Consumer<T>) consumer).accept(event);
                }
            }
        }
    }
}
