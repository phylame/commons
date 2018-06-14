package pw.phylame.commons.vdm;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface VdmWriter extends Closeable {
    void setComment(String comment);

    VdmEntry newEntry(String name);

    OutputStream putEntry(VdmEntry entry) throws IOException;

    default OutputStream putEntry(String name) throws IOException {
        return putEntry(newEntry(name));
    }

    void closeEntry(VdmEntry entry) throws IOException;
}
