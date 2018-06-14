package pw.phylame.commons;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;

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

    public static <E> Optional<E> firstOf(Collection<E> c) {
        if (isEmpty(c)) {
            return Optional.empty();
        } else if (c instanceof List<?>) {
            return Optional.ofNullable(((List<E>) c).get(0));
        } else {
            val it = c.iterator();
            if (it.hasNext()) {
                return Optional.ofNullable(it.next());
            } else {
                return Optional.empty();
            }
        }
    }

    public static <E> Iterable<E> iterable(@NonNull Iterator<E> i) {
        return () -> i;
    }

    public static <E> Iterator<E> iterator(@NonNull Enumeration<E> e) {
        return new EnumerationIterator<>(e);
    }

    public static void copy(Properties props, Map<? super String, ? super String> map) {
        if (!props.isEmpty()) {
            for (val entry : props.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    @RequiredArgsConstructor
    private static class EnumerationIterator<E> implements Iterator<E> {
        private final Enumeration<E> e;

        @Override
        public boolean hasNext() {
            return e.hasMoreElements();
        }

        @Override
        public E next() {
            return e.nextElement();
        }
    }
}
