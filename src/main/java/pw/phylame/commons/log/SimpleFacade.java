package pw.phylame.commons.log;

import lombok.val;

import java.time.LocalDateTime;

import static pw.phylame.commons.Reflections.currentThreadName;
import static pw.phylame.commons.text.StringUtils.abbreviate;

public class SimpleFacade implements LogFacade {
    public static final SimpleFacade INSTANCE = new SimpleFacade();

    private SimpleFacade() {
    }

    @Override
    public void setLevel(LogLevel level) {
    }

    @Override
    public void log(String tag, LogLevel level, String message) {
        print(tag, level, message);
    }

    @Override
    public void log(String tag, LogLevel level, String message, Throwable throwable) {
        print(tag, level, message);
        throwable.printStackTrace();
    }

    private void print(String tag, LogLevel lv, String msg) {
        val text = String.format("[%s] [%12s] %c/%s - %s",
                LocalDateTime.now(), abbreviate(currentThreadName(), 12), lv.name().charAt(0), tag, msg);
        if (lv == LogLevel.ERROR) {
            System.err.println(text);
        } else {
            System.out.println(text);
        }
    }
}
