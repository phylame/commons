package pw.phylame.commons.io;

import java.io.IOException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/30
 */
@FunctionalInterface
public interface IOConsumer<T> {
    void accept(T t) throws IOException;
}
