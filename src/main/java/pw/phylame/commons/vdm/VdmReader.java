package pw.phylame.commons.vdm;

import lombok.val;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface VdmReader extends Iterable<VdmEntry>, Closeable {
    int size();

    String getName();

    String getComment();

    VdmEntry getEntry(String name) throws IOException;

    InputStream openStream(VdmEntry entry) throws IOException;

    default InputStream openStream(String name) throws IOException {
        val entry = getEntry(name);
        return entry != null ? openStream(entry) : null;
    }
}
