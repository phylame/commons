package pw.phylame.commons.text;

import lombok.NonNull;
import lombok.val;

import java.util.*;

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

    public static String trimToEpmty(String str) {
        str = trim(str);
        return str != null ? str : "";
    }

    public static String trimStart(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int begin = 0;
        val length = str.length();
        while (begin < length && Character.isWhitespace(str.charAt(begin))) {
            ++begin;
        }
        return str.substring(begin);
    }

    public static String trimEnd(String str) {
        if (isEmpty(str)) {
            return str;
        }
        val length = str.length();
        int end = length - 1;
        while (end >= 0 && Character.isWhitespace(str.charAt(end))) {
            --end;
        }
        return str.substring(0, end);
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

    public static List<String> split(String str, String separator, int limit) {
        if (limit <= 0) {
            return Arrays.asList(str.split(separator));
        }
        int i, pos = 0;
        val width = separator.length();
        val results = new ArrayList<String>();
        for (i = 1; i < limit; ++i) {
            val begin = str.indexOf(separator, pos);
            if (begin < 0) {
                results.add(str.substring(pos));
                break;
            }
            results.add(str.substring(pos, begin));
            pos = begin + width;
        }
        if (i == limit) {
            results.add(str.substring(pos));
        }
        return Collections.unmodifiableList(results);
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

    public static String getValue(String str, String name, String partSeparator) {
        return getValue(str, name, "=", partSeparator, true);
    }

    public static String getValue(String str, String name, String partSeparator, boolean caseSensitive) {
        return getValue(str, name, "=", partSeparator, caseSensitive);
    }

    public static String getValue(String str,
                                  @NonNull String name,
                                  @NonNull String valueSeparator,
                                  @NonNull String partSeparator,
                                  boolean caseSensitive) {
        if (isEmpty(str)) {
            return null;
        }
        val parts = split(str, partSeparator, 0);
        for (String part : parts) {
            part = part.trim();
            if (isEmpty(part)) {
                continue;
            }
            val items = split(part, valueSeparator, 2);
            if (caseSensitive) {
                if (name.equals(items.get(0))) {
                    return items.size() > 1 ? items.get(1) : "";
                }
            } else if (name.equalsIgnoreCase(items.get(0))) {
                return items.size() > 1 ? items.get(1) : "";
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getValue("name=wp&age&sex=female", "age", "&"));
    }
}
