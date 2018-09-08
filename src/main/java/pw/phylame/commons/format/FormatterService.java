package pw.phylame.commons.format;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import pw.phylame.commons.DateUtils;
import pw.phylame.commons.IllegalImplementationException;
import pw.phylame.commons.value.Lazy;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class FormatterService {
    private static final Lazy<FormatterService> INSTANCE = Lazy.of(() -> {
        val fs = new FormatterService();
        fs.registerDefaults();
        return fs;
    });

    public static FormatterService getDefault() {
        return INSTANCE.get();
    }

    private final Map<Class<?>, Formatter<?>> registry = new IdentityHashMap<>();

    public boolean contains(Class<?> type) {
        return type != null && registry.containsKey(type);
    }

    public <T> void register(@NonNull Class<T> type, @NonNull Formatter<T> formatter) {
        registry.put(type, formatter);
    }

    public <T extends Enum<T>> void registerEnum(@NonNull Class<T> enumType) {
        register(enumType, new DefaultFormatter<>(enumType));
    }

    public void unregister(@NonNull Class<?> type) {
        registry.remove(type);
    }

    @SuppressWarnings("unchecked")
    public String render(@NonNull Object obj) {
        return !(obj instanceof CharSequence) ? render(obj, (Class<Object>) obj.getClass()) : obj.toString();
    }

    @SuppressWarnings("unchecked")
    public <T> String render(@NonNull T obj, @NonNull Class<T> type) {
        // call toString() directly when obj is CharSequence
        if (CharSequence.class.isAssignableFrom(type)) {
            return obj.toString();
        }
        val formatter = (Formatter<T>) registry.get(type);
        if (formatter != null) {
            val str = formatter.render(obj);
            if (str == null) {
                throw new IllegalImplementationException(formatter.getClass().getName() + ".render(T) returned null");
            }
            return str;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(@NonNull String str, @NonNull Class<T> type) {
        // return the str when parse to CharSequence
        if (CharSequence.class.isAssignableFrom(type)) {
            return (T) str;
        }
        val formatter = (Formatter<T>) registry.get(type);
        if (formatter != null) {
            val obj = formatter.parse(str);
            if (obj == null) {
                throw new IllegalImplementationException(formatter.getClass().getName() + ".parse(String) returned null");
            }
            return obj;
        }
        return null;
    }

    public void registerDefaults() {
        register(Byte.class, new DefaultFormatter<>(Byte.class));
        register(Short.class, new DefaultFormatter<>(Short.class));
        register(Integer.class, new DefaultFormatter<>(Integer.class));
        register(Long.class, new DefaultFormatter<>(Long.class));
        register(Float.class, new DefaultFormatter<>(Float.class));
        register(Double.class, new DefaultFormatter<>(Double.class));
        register(Boolean.class, new DefaultFormatter<>(Boolean.class));

        register(Locale.class, new DefaultFormatter<>(Locale.class));
        register(Charset.class, new DefaultFormatter<>(Charset.class));

        register(Date.class, new DefaultFormatter<>(Date.class));
        register(LocalDate.class, new DefaultFormatter<>(LocalDate.class));
        register(LocalTime.class, new DefaultFormatter<>(LocalTime.class));
        register(LocalDateTime.class, new DefaultFormatter<>(LocalDateTime.class));
        register(Instant.class, new DefaultFormatter<>(Instant.class));
    }

    @RequiredArgsConstructor
    private static class DefaultFormatter<T> implements Formatter<T> {
        private final Class<T> type;

        @Override
        public String render(T obj) {
            if (Date.class.isAssignableFrom(type)) {
                return DateUtils.toISO((Date) obj);
            } else if (Enum.class.isAssignableFrom(type)) {
                return ((Enum<?>) obj).name();
            } else if (type == Locale.class) {
                return ((Locale) obj).toLanguageTag();
            } else if (type == Charset.class) {
                return ((Charset) obj).name();
            }
            return obj.toString();
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public T parse(String str) {
            if (type == Integer.class) {
                return (T) Integer.decode(str);
            } else if (type == Boolean.class) {
                return (T) Boolean.valueOf(str);
            } else if (type == Byte.class) {
                return (T) Byte.decode(str);
            } else if (type == Short.class) {
                return (T) Short.decode(str);
            } else if (type == Long.class) {
                return (T) Long.decode(str);
            } else if (type == Float.class) {
                return (T) Float.valueOf(str);
            } else if (type == Double.class) {
                return (T) Double.valueOf(str);
            } else if (type == Locale.class) {
                return (T) Locale.forLanguageTag(str);
            } else if (type == Charset.class) {
                return (T) Charset.forName(str);
            } else if (type == LocalTime.class) {
                return (T) LocalTime.parse(str);
            } else if (type == LocalDate.class) {
                return (T) LocalDate.parse(str);
            } else if (type == LocalDateTime.class) {
                return (T) LocalDateTime.parse(str);
            } else if (type == Instant.class) {
                return (T) Instant.parse(str);
            } else if (type == Date.class) {
                return (T) DateUtils.fromISO(str);
            } else if (Enum.class.isAssignableFrom(type)) {
                return (T) Enum.valueOf((Class<? extends Enum>) type, str);
            }
            throw new UnsupportedOperationException();
        }
    }
}
