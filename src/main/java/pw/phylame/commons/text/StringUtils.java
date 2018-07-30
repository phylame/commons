package pw.phylame.commons.text;

import lombok.NonNull;
import lombok.val;
import lombok.var;
import pw.phylame.commons.value.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Utilities for string.
 *
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class StringUtils {
    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence text) {
        return text != null && text.length() != 0;
    }

    public static boolean isBlank(CharSequence text) {
        return !isNotBlank(text);
    }

    public static boolean isNotBlank(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        for (int i = 0, end = text.length(); i < end; i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerCase(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        for (int i = 0, end = text.length(); i != end; ++i) {
            if (Character.isUpperCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUpperCase(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        for (int i = 0, end = text.length(); i != end; ++i) {
            if (Character.isLowerCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int count(String text, String target) {
        if (isEmpty(text) || isEmpty(target)) {
            return 0;
        }

        var counter = 0;
        var position = 0;
        val length = target.length();
        while (true) {
            val i = text.indexOf(target, position);
            if (i < 0) {
                break;
            }
            ++counter;
            position = i + length;
        }

        return counter;
    }

    public static int size(int x) {
        int d = 1;
        if (x >= 0) {
            d = 0;
            x = -x;
        }
        int p = -10;
        for (int i = 1; i < 10; i++) {
            if (x > p) {
                return i + d;
            }
            p = 10 * p;
        }
        return 10 + d;
    }

    public static String repeat(CharSequence text, int count) {
        val b = new StringBuilder();
        for (int i = 0; i < count; i++) {
            b.append(text);
        }
        return b.toString();
    }

    public static String coalesce(String text, String emptyDefault) {
        return isNotEmpty(text) ? text : emptyDefault;
    }

    public static String coalesce(String text, @NonNull Supplier<String> emptySupplier) {
        return isNotEmpty(text) ? text : emptySupplier.get();
    }

    public static String trim(String text) {
        if (isEmpty(text)) {
            return text;
        }

        int begin = 0;
        int end = text.length();
        while (begin < end && Character.isWhitespace(text.charAt(begin))) {
            begin++;
        }
        while (begin < end && Character.isWhitespace(text.charAt(end - 1))) {
            end--;
        }

        return text.substring(begin, end);
    }

    public static String trimToNull(String text) {
        text = trim(text);
        return isNotEmpty(text) ? text : null;
    }

    public static String trimToEpmty(String text) {
        text = trim(text);
        return text != null ? text : "";
    }

    public static String trimStart(String text) {
        if (isEmpty(text)) {
            return text;
        }
        int begin = 0;
        val length = text.length();
        while (begin < length && Character.isWhitespace(text.charAt(begin))) {
            ++begin;
        }
        return text.substring(begin);
    }

    public static String trimEnd(String text) {
        if (isEmpty(text)) {
            return text;
        }
        int end = text.length() - 1;
        while (end >= 0 && Character.isWhitespace(text.charAt(end))) {
            --end;
        }
        return text.substring(0, end);
    }

    public static String removeStart(String text, String prefix) {
        if (isEmpty(text) || isEmpty(prefix)) {
            return text;
        } else if (text.startsWith(prefix)) {
            return text.substring(prefix.length());
        } else {
            return text;
        }
    }

    public static String removeEnd(String text, String suffix) {
        if (isEmpty(text) || isEmpty(suffix)) {
            return text;
        } else if (text.endsWith(suffix)) {
            return text.substring(0, text.length() - suffix.length());
        } else {
            return text;
        }
    }

    public static String replace(String text, String target, @NonNull String replacement, int limit) {
        if (isEmpty(text) || isEmpty(target) || limit <= 0) {
            return text;
        }
        var begin = 0;
        val length = target.length();
        val b = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            val index = text.indexOf(target, begin);
            if (index < 0) {
                break;
            }
            b.append(text, begin, index).append(replacement);
            begin = index + length;
        }
        b.append(text.substring(begin));
        return b.toString();
    }

    public static String replaceFirst(String text, String target, @NonNull String replacement) {
        if (isEmpty(text) || isEmpty(target)) {
            return text;
        }
        val index = text.indexOf(target);
        return index != -1
                ? text.substring(0, index) + replacement + text.substring(index + target.length())
                : text;
    }

    public static String replaceLast(String text, String target, @NonNull String replacement) {
        if (isEmpty(text) || isEmpty(target)) {
            return text;
        }
        val index = text.lastIndexOf(target);
        return index != -1
                ? text.substring(0, index) + replacement + text.substring(index + target.length())
                : text;
    }

    public static String[] split(@NonNull String text, @NonNull String separator, int limit) {
        if (limit <= 0) {
            return text.split(separator);
        }
        int i, pos = 0;
        val width = separator.length();
        val results = new ArrayList<String>(limit);
        for (i = 1; i < limit; ++i) {
            val begin = text.indexOf(separator, pos);
            if (begin == -1) {
                results.add(text.substring(pos));
                break;
            }
            results.add(text.substring(pos, begin));
            pos = begin + width;
        }
        if (i == limit) {
            results.add(text.substring(pos));
        }
        return results.toArray(new String[0]);
    }

    public static Pair<String, String> partition(String text, String separator) {
        if (isEmpty(text)) {
            return Pair.of("", "");
        } else if (isEmpty(separator)) {
            return Pair.of(text, "");
        }
        val index = text.indexOf(separator);
        return index != -1
                ? Pair.of(text.substring(0, index), text.substring(index + separator.length()))
                : Pair.of(text, "");
    }

    public static String getFirst(String text, String separator) {
        if (isEmpty(text)) {
            return text;
        } else if (isEmpty(separator)) {
            return text;
        }
        val index = text.indexOf(separator);
        return index != -1 ? text.substring(0, index) : "";
    }

    public static String getSecond(String text, String separator) {
        if (isEmpty(text)) {
            return text;
        } else if (isEmpty(separator)) {
            return text;
        }
        val index = text.indexOf(separator);
        return index != -1 ? text.substring(index + separator.length()) : "";
    }

    public static <E> String join(@NonNull Iterable<E> items, @NonNull CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(items.iterator())
                .separator(separator)
                .build()
                .join();
    }

    public static <E> String join(@NonNull Iterator<E> iterator, @NonNull CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(iterator)
                .separator(separator)
                .build()
                .join();
    }

    public static String getValue(String text, String name, String partSeparator) {
        return getValue(text, name, "=", partSeparator, true);
    }

    public static String getValue(String text, String name, String partSeparator, boolean caseSensitive) {
        return getValue(text, name, "=", partSeparator, caseSensitive);
    }

    public static String getValue(String text, @NonNull String name, @NonNull String valueSeparator, @NonNull String partSeparator, boolean caseSensitive) {
        if (isEmpty(text)) {
            return null;
        }
        val parts = split(text, partSeparator, 0);
        for (String part : parts) {
            part = part.trim();
            if (isEmpty(part)) {
                continue;
            }
            val pair = partition(part, valueSeparator);
            if (caseSensitive) {
                if (name.equals(pair.getFirst())) {
                    return pair.getSecond();
                }
            } else if (name.equalsIgnoreCase(pair.getFirst())) {
                return pair.getSecond();
            }
        }
        return null;
    }
}
