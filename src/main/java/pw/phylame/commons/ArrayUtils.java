package pw.phylame.commons;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class ArrayUtils {
    public static boolean isEmpty(Object[] a) {
        return a == null || a.length == 0;
    }

    public static boolean isNotEmpty(Object[] a) {
        return a != null && a.length != 0;
    }
}
