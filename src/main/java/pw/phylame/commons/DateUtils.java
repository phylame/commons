package pw.phylame.commons;

import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
public final class DateUtils {
    private static final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String toISO(@NonNull Date date) {
        return new SimpleDateFormat(ISO_DATE_PATTERN).format(date);
    }

    public static Date fromISO(@NonNull String str) {
        try {
            return new SimpleDateFormat(ISO_DATE_PATTERN).parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + str);
        }
    }
}
