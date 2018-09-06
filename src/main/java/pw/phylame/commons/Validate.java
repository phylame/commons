package pw.phylame.commons;

import pw.phylame.commons.text.StringUtils;

/**
 * Utilities for assert and validation.
 *
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class Validate {
    public static void require(boolean cond, String msg) {
        if (!cond) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static <T> T nonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static <T> T nonNull(T obj, String msg) {
        if (obj == null) {
            throw new NullPointerException(msg);
        }
        return obj;
    }

    public static <S extends CharSequence> S nonEmpty(S str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException();
        }
        return str;
    }

    public static <S extends CharSequence> S nonEmpty(S str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(msg);
        }
        return str;
    }
}
