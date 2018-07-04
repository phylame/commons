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
    public static final char FULL_WIDTH_SPACE = '\u3000';

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() != 0;
    }

    public static boolean isBlank(CharSequence cs) {
        return !isNotBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        char ch;
        for (int i = 0, end = cs.length(); i < end; i++) {
            if ((ch = cs.charAt(i)) != FULL_WIDTH_SPACE && !Character.isWhitespace(ch)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLowerCase(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0, end = cs.length(); i != end; ++i) {
            if (Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUpperCase(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0, end = cs.length(); i != end; ++i) {
            if (Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int count(String str, String target) {
        if (isEmpty(str) || isEmpty(target)) {
            return 0;
        }

        var counter = 0;
        var position = 0;
        val length = target.length();
        while (true) {
            val i = str.indexOf(target, position);
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

    public static String repeat(CharSequence cs, int count) {
        val b = new StringBuilder();
        for (int i = 0; i < count; i++) {
            b.append(cs);
        }
        return b.toString();
    }

    public static String coalesce(String str, String emptyDefault) {
        return isNotEmpty(str) ? str : emptyDefault;
    }

    public static String coalesce(String str, @NonNull Supplier<String> emptySupplier) {
        return isNotEmpty(str) ? str : emptySupplier.get();
    }

    public static String trim(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char ch;
        int end = str.length();
        int begin = 0;
        while (begin < end && ((ch = str.charAt(begin)) == FULL_WIDTH_SPACE || Character.isWhitespace(ch))) {
            begin++;
        }
        while (begin < end && ((ch = str.charAt(end - 1)) == FULL_WIDTH_SPACE || Character.isWhitespace(ch))) {
            end--;
        }

        return str.substring(begin, end);
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
        char ch;
        int begin = 0;
        val length = str.length();
        while (begin < length && ((ch = str.charAt(begin)) == FULL_WIDTH_SPACE || Character.isWhitespace(ch))) {
            ++begin;
        }
        return str.substring(begin);
    }

    public static String trimEnd(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char ch;
        val length = str.length();
        int end = length - 1;
        while (end >= 0 && ((ch = str.charAt(end)) == FULL_WIDTH_SPACE || Character.isWhitespace(ch))) {
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

    public static String replace(String str, String target, @NonNull String replacement, int limit) {
        if (isEmpty(str) || isEmpty(target) || limit <= 0) {
            return str;
        }
        var begin = 0;
        val length = target.length();
        val b = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            val index = str.indexOf(target, begin);
            if (index < 0) {
                break;
            }
            b.append(str, begin, index).append(replacement);
            begin = index + length;
        }
        b.append(str.substring(begin));
        return b.toString();
    }

    public static String replaceFirst(String str, String target, @NonNull String replacement) {
        if (isEmpty(str) || isEmpty(target)) {
            return str;
        }
        val index = str.indexOf(target);
        return index != -1
                ? str.substring(0, index) + replacement + str.substring(index + target.length())
                : str;
    }

    public static String replaceLast(String str, String target, @NonNull String replacement) {
        if (isEmpty(str) || isEmpty(target)) {
            return str;
        }
        val index = str.lastIndexOf(target);
        return index != -1
                ? str.substring(0, index) + replacement + str.substring(index + target.length())
                : str;
    }

    public static String[] split(@NonNull String str, @NonNull String separator, int limit) {
        if (limit <= 0) {
            return str.split(separator);
        }
        int i, pos = 0;
        val width = separator.length();
        val results = new ArrayList<String>();
        for (i = 1; i < limit; ++i) {
            val begin = str.indexOf(separator, pos);
            if (begin == -1) {
                results.add(str.substring(pos));
                break;
            }
            results.add(str.substring(pos, begin));
            pos = begin + width;
        }
        if (i == limit) {
            results.add(str.substring(pos));
        }
        return results.toArray(new String[0]);
    }

    public static Pair<String, String> partition(String str, String separator) {
        if (isEmpty(str)) {
            return Pair.of("", "");
        } else if (isEmpty(separator)) {
            return Pair.of(str, "");
        }
        val index = str.indexOf(separator);
        return index != -1
                ? Pair.of(str.substring(0, index), str.substring(index + separator.length()))
                : Pair.of(str, "");
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
