package pw.phylame.commons;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.value.Keyed;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class CollectionUtils {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return m != null && !m.isEmpty();
    }

    public static <E> Optional<E> firstOf(Collection<E> c) {
        if (isEmpty(c)) {
            return Optional.empty();
        } else if (c instanceof List<?>) {
            return Optional.ofNullable(((List<E>) c).get(0));
        } else {
            val it = c.iterator();
            return it.hasNext()
                    ? Optional.ofNullable(it.next())
                    : Optional.empty();
        }
    }

    public static <E> Optional<E> anyOf(List<E> list) {
        return isNotEmpty(list)
                ? Optional.ofNullable(list.get(ThreadLocalRandom.current().nextInt(list.size())))
                : Optional.empty();
    }

    public static void copy(Properties props, Map<? super String, ? super String> map) {
        if (!props.isEmpty()) {
            for (val entry : props.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    public static <K, T extends Keyed<K>> void copy(Iterable<T> items, Map<K, T> map) {
        for (T item : items) {
            map.put(item.getKey(), item);
        }
    }

    public static <E> Iterable<E> iterable(@NonNull Iterator<E> i) {
        return () -> i;
    }

    public static <E> Iterator<E> iterator(@NonNull Enumeration<E> e) {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public E next() {
                return e.nextElement();
            }
        };
    }

    public static <E> Set<E> setOf(E a) {
        return Collections.singleton(a);
    }

    public static <E> Set<E> setOf(E a, E b) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        return Collections.unmodifiableSet(set);
    }

    public static <E> Set<E> setOf(E a, E b, E c) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        return Collections.unmodifiableSet(set);
    }

    @SafeVarargs
    public static <E> Set<E> setOf(E... a) {
        val set = new HashSet<E>();
        Collections.addAll(set, a);
        return Collections.unmodifiableSet(set);
    }
}
