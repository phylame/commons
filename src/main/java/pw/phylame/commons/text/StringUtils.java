package pw.phylame.commons.text;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
public final class StringUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return cs != null && cs.length() != 0;
    }
}
