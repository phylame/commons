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
    public static final String EMPTY = "";

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence text) {
        return !isEmpty(text);
    }

    public static boolean isBlank(CharSequence text) {
        int length;
        if (text == null || (length = text.length()) == 0) {
            return true;
        }
        for (int i = 0; i != length; ++i) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence text) {
        return !isBlank(text);
    }

    public static boolean isLowerCase(CharSequence text) {
        int length;
        if (text == null || (length = text.length()) == 0) {
            return false;
        }
        for (int i = 0; i != length; ++i) {
            if (!Character.isLowerCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUpperCase(CharSequence text) {
        int length;
        if (text == null || (length = text.length()) == 0) {
            return false;
        }
        for (int i = 0; i != length; ++i) {
            if (!Character.isUpperCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isLetter(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(CharSequence text) {
        if (text == null) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isLetter(text.charAt(i)) && text.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumeric(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericSpace(CharSequence text) {
        if (text == null) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isLetterOrDigit(text.charAt(i)) && text.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(CharSequence text) {
        if (isEmpty(text)) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(CharSequence text) {
        if (text == null) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isDigit(text.charAt(i)) && text.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(CharSequence text) {
        if (text == null) {
            return false;
        }
        val length = text.length();
        for (int i = 0; i != length; ++i) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int size(int n) {
        int d = 1;
        if (n >= 0) {
            d = 0;
            n = -n;
        }
        int p = -10;
        for (int i = 1; i != 10; ++i) {
            if (n > p) {
                return i + d;
            }
            p = 10 * p;
        }
        return 10 + d;
    }

    public static int count(String text, String target) {
        if (isEmpty(text) || isEmpty(target)) {
            return 0;
        }
        int count = 0;
        int index = 0;
        val length = target.length();
        while ((index = text.indexOf(target, index)) != -1) {
            index += length;
            ++count;
        }
        return count;
    }

    public static String repeat(CharSequence text, int count) {
        val sb = new StringBuilder();
        for (int i = 0; i != count; ++i) {
            sb.append(text);
        }
        return sb.toString();
    }

    public static String coalesce(String text, String emptyDefault) {
        return isNotEmpty(text) ? text : emptyDefault;
    }

    public static String coalesce(String text, @NonNull Supplier<String> emptySupplier) {
        return isNotEmpty(text) ? text : emptySupplier.get();
    }

    public static String trim(String text) {
        int end;
        if (text == null || (end = text.length()) == 0) {
            return text;
        }
        int begin = 0;
        while (begin != end && Character.isWhitespace(text.charAt(begin))) {
            ++begin;
        }
        while (begin != end && Character.isWhitespace(text.charAt(end - 1))) {
            --end;
        }
        return text.substring(begin, end);
    }

    public static String trimToNull(String text) {
        val ts = trim(text);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEpmty(String text) {
        return text == null ? EMPTY : trim(text);
    }

    public static String trimStart(String text) {
        return stripStart(text, null);
    }

    public static String trimEnd(String text) {
        return stripEnd(text, null);
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        str = strip(str, null);
        return str.isEmpty() ? null : str;
    }

    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        return stripEnd(stripStart(str, stripChars), stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }
        int begin = 0;
        if (stripChars == null) {
            while (begin != length && Character.isWhitespace(str.charAt(begin))) {
                ++begin;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (begin != length && stripChars.indexOf(str.charAt(begin)) != -1) {
                ++begin;
            }
        }
        return str.substring(begin);
    }

    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                --end;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                --end;
            }
        }
        return str.substring(0, end);
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

    public static String replace(String text, String target, String replacement, int limit) {
        if (isEmpty(text) || isEmpty(target) || limit <= 0) {
            return text;
        }
        var begin = 0;
        val length = target.length();
        val sb = new StringBuilder();
        for (int i = 0; i != limit; ++i) {
            val index = text.indexOf(target, begin);
            if (index < 0) {
                break;
            }
            sb.append(text, begin, index).append(replacement);
            begin = index + length;
        }
        sb.append(text.substring(begin));
        return sb.toString();
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

    public static String substring(String str, int begin) {
        if (str == null) {
            return null;
        }
        if (begin < 0) {
            begin = str.length() + begin;
        }
        if (begin < 0) {
            begin = 0;
        }
        if (begin > str.length()) {
            return EMPTY;
        }
        return str.substring(begin);
    }

    public static String substring(String str, int begin, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (begin < 0) {
            begin = str.length() + begin;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (begin > end) {
            return EMPTY;
        }
        if (begin < 0) {
            begin = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(begin, end);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String mid(String str, int off, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || off > str.length()) {
            return EMPTY;
        }
        if (off < 0) {
            off = 0;
        }
        if (str.length() <= off + len) {
            return str.substring(off);
        }
        return str.substring(off, off + len);
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, "...", 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        return abbreviate(str, "...", offset, maxWidth);
    }

    public static String abbreviate(String str, String abbrevMarker, int maxWidth) {
        return abbreviate(str, abbrevMarker, 0, maxWidth);
    }

    public static String abbreviate(String str, String abbrevMarker, int offset, int maxWidth) {
        if (isEmpty(str) || isEmpty(abbrevMarker)) {
            return str;
        }

        val abbrevMarkerLength = abbrevMarker.length();
        val minAbbrevWidth = abbrevMarkerLength + 1;
        val minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;

        if (maxWidth < minAbbrevWidth) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        if (offset > str.length()) {
            offset = str.length();
        }
        if (str.length() - offset < maxWidth - abbrevMarkerLength) {
            offset = str.length() - (maxWidth - abbrevMarkerLength);
        }
        if (offset <= abbrevMarkerLength + 1) {
            return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
        }
        if (maxWidth < minAbbrevWidthOffset) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is " + minAbbrevWidthOffset);
        }
        if (offset + maxWidth - abbrevMarkerLength < str.length()) {
            return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength);
        }
        return abbrevMarker + str.substring(str.length() - (maxWidth - abbrevMarkerLength));
    }

    public static String abbreviateMiddle(String str, String middle, int length) {
        if (isEmpty(str) || isEmpty(middle)) {
            return str;
        }

        if (length >= str.length() || length < middle.length() + 2) {
            return str;
        }

        val target = length - middle.length();
        val begin = target / 2 + target % 2;
        val end = str.length() - target / 2;

        return str.substring(0, begin) + middle + str.substring(end);
    }

    public static String[] split(String text, String separator, int limit) {
        if (limit <= 0) {
            return text.split(separator);
        }
        int i, pos = 0;
        val width = separator.length();
        val results = new ArrayList<String>(limit);
        for (i = 1; i != limit; ++i) {
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
            return Pair.of(EMPTY, EMPTY);
        } else if (isEmpty(separator)) {
            return Pair.of(text, EMPTY);
        }
        val index = text.indexOf(separator);
        return index != -1
                ? Pair.of(text.substring(0, index), text.substring(index + separator.length()))
                : Pair.of(text, EMPTY);
    }

    public static String getFirst(String text, String separator) {
        if (isEmpty(text)) {
            return text;
        } else if (isEmpty(separator)) {
            return text;
        }
        val index = text.indexOf(separator);
        return index != -1 ? text.substring(0, index) : EMPTY;
    }

    public static String getSecond(String text, String separator) {
        if (isEmpty(text)) {
            return text;
        } else if (isEmpty(separator)) {
            return text;
        }
        val index = text.indexOf(separator);
        return index != -1 ? text.substring(index + separator.length()) : EMPTY;
    }

    public static <E> String join(Iterable<E> items, CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(items.iterator())
                .separator(separator)
                .build()
                .join();
    }

    public static <E> String join(Iterator<E> iterator, CharSequence separator) {
        return StringJoiner.<E>builder()
                .iterator(iterator)
                .separator(separator)
                .build()
                .join();
    }

    public static String capitalize(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return str;
        }

        val firstCodePoint = str.codePointAt(0);
        val newCodePoint = Character.toTitleCase(firstCodePoint);
        if (firstCodePoint == newCodePoint) {
            return str;
        }

        int outOffset = 0;
        val codePoints = new int[length];
        codePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodePoint); inOffset < length; ) {
            val codePoint = str.codePointAt(inOffset);
            codePoints[outOffset++] = codePoint;
            inOffset += Character.charCount(codePoint);
        }
        return new String(codePoints, 0, outOffset);
    }

    public static String getValue(String text, String name, String partSeparator) {
        return getValue(text, name, "=", partSeparator, true);
    }

    public static String getValue(String text, String name, String partSeparator, boolean caseSensitive) {
        return getValue(text, name, "=", partSeparator, caseSensitive);
    }

    public static String getValue(String text, String name, String valueSeparator, String partSeparator, boolean caseSensitive) {
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
