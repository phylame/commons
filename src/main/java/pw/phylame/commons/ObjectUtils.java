package pw.phylame.commons;

/**
 * @author wp <phylame@163.com>
 * @date 2018/07/09
 */
public final class ObjectUtils {
    public static <T extends Comparable<T>> int compare(T a, T b) {
        if (a == null) {
            if (b == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (b == null) {
            return 1;
        } else {
            return a.compareTo(b);
        }
    }
}
