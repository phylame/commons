package pw.phylame.commons;

import java.util.function.Predicate;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/14
 */
public final class Functions {
    public static <T> Predicate<T> isSame(T other) {
        return self -> self == other;
    }

    public static <T> Predicate<T> isNotSame(T other) {
        return self -> self != other;
    }
}
