package pw.phylame.commons.setting;

import lombok.val;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class SettingsUtils {
    public static Object get(Settings settings, String key) {
        return settings != null ? settings.get(key) : null;
    }

    public static <T> T get(Settings settings, String key, Class<T> type) {
        if (settings == null) {
            return null;
        }
        if (settings instanceof AbstractSettings) {
            return ((AbstractSettings) settings).get(key, type);
        }
        val value = settings.get(key);
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("`" + key + "` is not " + type);
        }
        return type.cast(value);
    }

    public static Integer getInt(Settings settings, String key) {
        return get(settings, key, Integer.class);
    }

    public static Boolean getBool(Settings settings, String key) {
        return get(settings, key, Boolean.class);
    }

    public static Double getDouble(Settings settings, String key) {
        return get(settings, key, Double.class);
    }

    public static String getString(Settings settings, String key) {
        val value = get(settings, key);
        if (value == null) {
            return null;
        }
        if (value instanceof CharSequence) {
            return value.toString();
        }
        throw new IllegalArgumentException("`" + key + "` is not String");
    }

    public static Object set(Settings settings, String key, Object value) {
        return settings != null ? settings.set(key, value) : null;
    }
}
