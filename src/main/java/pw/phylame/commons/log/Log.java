package pw.phylame.commons.log;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import lombok.var;
import pw.phylame.commons.text.StringUtils;

public final class Log {
    public static final String DEFAULT_LEVEL_KEY = Log.class.getName() + ".level";
    public static final String DEFAULT_FACADE_KEY = Log.class.getName() + ".facade";

    @Getter
    private static LogLevel level = LogLevel.INFO;

    public static void setLevel(@NonNull LogLevel level) {
        if (Log.level != level) {
            Log.level = level;
            facade.setLevel(level);
        }
    }

    @Getter
    private static LogFacade facade;

    public static void setFacade(@NonNull LogFacade facade) {
        if (Log.facade != facade) {
            Log.facade = facade;
            facade.setLevel(level);
        }
    }

    public static boolean isEnable(LogLevel level) {
        return level.value <= Log.level.value;
    }

    public static void t(String tag, String msg) {
        if (isEnable(LogLevel.TRACE)) {
            print(tag, LogLevel.TRACE, msg);
        }
    }

    public static void t(String tag, String msg, Object arg) {
        if (isEnable(LogLevel.TRACE)) {
            print(tag, LogLevel.TRACE, msg, arg);
        }
    }

    public static void t(String tag, String msg, Object arg1, Object arg2) {
        if (isEnable(LogLevel.TRACE)) {
            print(tag, LogLevel.TRACE, msg, arg1, arg2);
        }
    }

    public static void t(String tag, String msg, Object... args) {
        if (isEnable(LogLevel.TRACE)) {
            print(tag, LogLevel.TRACE, msg, args);
        }
    }

    public static void t(String tag, String msg, Throwable t) {
        if (isEnable(LogLevel.TRACE)) {
            print(tag, LogLevel.TRACE, msg, t);
        }
    }

    public static void d(String tag, String msg) {
        if (isEnable(LogLevel.DEBUG)) {
            print(tag, LogLevel.DEBUG, msg);
        }
    }

    public static void d(String tag, String msg, Object arg) {
        if (isEnable(LogLevel.DEBUG)) {
            print(tag, LogLevel.DEBUG, msg, arg);
        }
    }

    public static void d(String tag, String msg, Object arg1, Object arg2) {
        if (isEnable(LogLevel.DEBUG)) {
            print(tag, LogLevel.DEBUG, msg, arg1, arg2);
        }
    }

    public static void d(String tag, String msg, Object... args) {
        if (isEnable(LogLevel.DEBUG)) {
            print(tag, LogLevel.DEBUG, msg, args);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isEnable(LogLevel.DEBUG)) {
            print(tag, LogLevel.DEBUG, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        if (isEnable(LogLevel.INFO)) {
            print(tag, LogLevel.INFO, msg);
        }
    }

    public static void i(String tag, String msg, Object arg) {
        if (isEnable(LogLevel.INFO)) {
            print(tag, LogLevel.INFO, msg, arg);
        }
    }

    public static void i(String tag, String msg, Object arg1, Object arg2) {
        if (isEnable(LogLevel.INFO)) {
            print(tag, LogLevel.INFO, msg, arg1, arg2);
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (isEnable(LogLevel.INFO)) {
            print(tag, LogLevel.INFO, msg, args);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isEnable(LogLevel.INFO)) {
            print(tag, LogLevel.INFO, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        if (isEnable(LogLevel.WARN)) {
            print(tag, LogLevel.WARN, msg);
        }
    }

    public static void w(String tag, String msg, Object arg) {
        if (isEnable(LogLevel.WARN)) {
            print(tag, LogLevel.WARN, msg, arg);
        }
    }

    public static void w(String tag, String msg, Object arg1, Object arg2) {
        if (isEnable(LogLevel.WARN)) {
            print(tag, LogLevel.WARN, msg, arg1, arg2);
        }
    }

    public static void w(String tag, String msg, Object... args) {
        if (isEnable(LogLevel.WARN)) {
            print(tag, LogLevel.WARN, msg, args);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isEnable(LogLevel.WARN)) {
            print(tag, LogLevel.WARN, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (isEnable(LogLevel.ERROR)) {
            print(tag, LogLevel.ERROR, msg);
        }
    }

    public static void e(String tag, String msg, Object arg) {
        if (isEnable(LogLevel.ERROR)) {
            print(tag, LogLevel.ERROR, msg, arg);
        }
    }

    public static void e(String tag, String msg, Object arg1, Object arg2) {
        if (isEnable(LogLevel.ERROR)) {
            print(tag, LogLevel.ERROR, msg, arg1, arg2);
        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (isEnable(LogLevel.ERROR)) {
            print(tag, LogLevel.ERROR, msg, args);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isEnable(LogLevel.ERROR)) {
            print(tag, LogLevel.ERROR, msg, t);
        }
    }

    private static void print(String tag, LogLevel level, String msg) {
        facade.log(tag, level, msg);
    }

    private static void print(String tag, LogLevel level, String msg, Object arg) {
        facade.log(tag, level, String.format(msg, arg));
    }

    private static void print(String tag, LogLevel level, String msg, Object arg1, Object arg2) {
        facade.log(tag, level, String.format(msg, arg1, arg2));
    }

    private static void print(String tag, LogLevel level, String msg, Object... args) {
        facade.log(tag, level, String.format(msg, args));
    }

    private static void print(String tag, LogLevel level, String msg, Throwable t) {
        facade.log(tag, level, msg, t);
    }

    private static void initLog() {
        var name = System.getProperty(DEFAULT_LEVEL_KEY);
        if (StringUtils.isNotEmpty(name)) {
            level = LogLevel.valueOf(name.toUpperCase());
        }
        name = System.getProperty(DEFAULT_FACADE_KEY);
        if (StringUtils.isEmpty(name) || name.equals("simple") || name.equals(SimpleFacade.class.getName())) {
            facade = SimpleFacade.INSTANCE;
        } else {
            try {
                val clazz = Class.forName(name);
                if (!LogFacade.class.isAssignableFrom(clazz)) {
                    throw new RuntimeException(name + " is not " + LogFacade.class);
                }
                facade = (LogFacade) clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Cannot load log facade: " + name, e);
            }
        }
        facade.setLevel(level);
    }

    static {
        initLog();
    }
}
