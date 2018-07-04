package pw.phylame.commons;

@SuppressWarnings("serial")
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
    }

    public NotImplementedException(String message) {
        super(message);
    }
}
