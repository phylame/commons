package pw.phylame.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface Persistable {
    void load(InputStream input) throws IOException;

    void sync(OutputStream output) throws IOException;
}
