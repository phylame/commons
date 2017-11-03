package pw.phylame.commons.text;

import java.util.Objects;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() != 0;
    }

    public static String coalesce(CharSequence cs, CharSequence fallback) {
        return isNotEmpty(cs) ? cs.toString() : Objects.toString(fallback, null);
    }
}
