package pw.phylame.commons;

import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
public final class DateUtils {
    public static final String ANSIC_PATTERN = "EEE MMM d HH:mm:ss z yyyy";

    public static final String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    public static final String RFC1036_PATTERN = "EEEEEE, dd-MMM-yy HH:mm:ss z";

    public static final String LOOSE_TIME_PATTERN = "H:m:s";

    public static final String LOOSE_DATE_PATTERN = "yyyy-M-d";

    public static final String LOOSE_DATE_TIME_PATTERN = "yyyy-M-d H:m:s";

    public static final String ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static String toISO(@NonNull Date date) {
        return new SimpleDateFormat(ISO_DATE_TIME_PATTERN).format(date);
    }

    public static Date fromISO(@NonNull String str) {
        try {
            return new SimpleDateFormat(ISO_DATE_TIME_PATTERN).parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + str);
        }
    }

    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    public static TemporalAccessor parse(CharSequence text, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).parse(text);
    }

    public static LocalDate parseDate(CharSequence text) {
        return parseDate(text, LOOSE_DATE_PATTERN);
    }

    public static LocalDate parseDate(CharSequence text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseTime(CharSequence text) {
        return parseTime(text, LOOSE_TIME_PATTERN);
    }

    public static LocalTime parseTime(CharSequence text, String pattern) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseDateTime(CharSequence text) {
        return parseDateTime(text, LOOSE_DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDateTime(CharSequence text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }
}
