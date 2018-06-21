package pw.phylame.commons.text;

import lombok.NonNull;

import java.util.Iterator;

/**
 * Utilities for string.
 *
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() != 0;
    }

    public static String trim(String str) {
        return str != null ? str.trim() : null;
    }

    public static String trimToNull(String str) {
        str = trim(str);
        return isNotEmpty(str) ? str : null;
    }

    public static String removeStart(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        } else if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        } else {
            return str;
        }
    }

    public static String removeEnd(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        } else if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        } else {
            return str;
        }
    }

    public static <E> String join(@NonNull Iterable<E> items, CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(items.iterator())
                .separator(separator)
                .build()
                .join();
    }

    public static <E> String join(@NonNull Iterator<E> iterator, CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(iterator)
                .separator(separator)
                .build()
                .join();
    }
}
