package pw.phylame.commons.io;

import java.io.IOException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/30
 */
@FunctionalInterface
public interface IOFunction<T, R> {
    R apply(T t) throws IOException;
}
