package pw.phylame.commons.log;

public interface LogFacade {
    void setLevel(LogLevel level);

    void log(String tag, LogLevel level, String message);

    void log(String tag, LogLevel level, String message, Throwable throwable);
}
