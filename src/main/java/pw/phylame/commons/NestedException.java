package pw.phylame.commons;

/**
 * Unchecked exception wrapper for checked exception.
 *
 * @author wp <phylame@163.com>
 * @date 2018/06/08
 */
@SuppressWarnings("serial")
public class NestedException extends RuntimeException {
    public NestedException(Throwable cause) {
        super(cause);
    }
}
