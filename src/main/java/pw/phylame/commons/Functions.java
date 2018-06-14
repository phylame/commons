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

    public static <T extends Comparable<T>> Predicate<T> lessThan(T other) {
        return self -> {
            if (self == null) {
                return true;
            } else if (other == null) {
                return false;
            } else {
                return self.compareTo(other) < 0;
            }
        };
    }

    public static <T extends Comparable<T>> Predicate<T> greaterThan(T other) {
        return self -> {
            if (self == null) {
                return other == null;
            } else if (other == null) {
                return true;
            } else {
                return self.compareTo(other) > 0;
            }
        };
    }
}
