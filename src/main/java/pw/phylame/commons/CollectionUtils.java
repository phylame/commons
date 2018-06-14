package pw.phylame.commons;

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

    public static void copy(Properties props, Map<? super String, ? super String> map) {
        if (!props.isEmpty()) {
            for (val entry : props.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }
}
