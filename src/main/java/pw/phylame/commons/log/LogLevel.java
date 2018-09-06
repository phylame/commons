package pw.phylame.commons.log;

public enum LogLevel {
    TRACE(5),
    DEBUG(4),
    INFO(3),
    WARN(2),
    ERROR(1);

    final int value;

    LogLevel(int value) {
        this.value = value;
    }
}
