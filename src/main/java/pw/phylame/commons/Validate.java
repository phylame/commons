package pw.phylame.commons;

import static pw.phylame.commons.text.StringUtils.isNotEmpty;

public final class Validate {
    private Validate() {
    }

    private static final String ERR_NULL = "object cannot be null";

    private static final String ERR_EMPTY = "string cannot be null or empty";

    public static void require(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void require(boolean condition, String msg, Object... args) {
        if (!condition) {
            throw new IllegalArgumentException(String.format(msg, args));
        }
    }

    public static void requireNotNull(Object obj) {
        require(obj != null, ERR_NULL);
    }

    public static void requireNotNull(Object obj, String msg) {
        require(obj != null, msg);
    }

    public static void requireNotNull(Object obj, String msg, Object... args) {
        require(obj != null, msg, args);
    }

    public static <T extends CharSequence> void requireNotEmpty(T str) {
        require(isNotEmpty(str), ERR_EMPTY);
    }

    public static <T extends CharSequence> void requireNotEmpty(T str, String msg) {
        require(isNotEmpty(str), msg);
    }

    public static <T extends CharSequence> void requireNotEmpty(T str, String msg, Object... args) {
        require(isNotEmpty(str), msg, args);
    }
}
